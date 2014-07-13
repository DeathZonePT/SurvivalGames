package com.Doctor.Stopbox.SG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Doctor.Stopbox.SG.commands.CommandHandler;
import com.Doctor.Stopbox.SG.commands.Cmd.Disable;
import com.Doctor.Stopbox.SG.commands.Cmd.Enable;
import com.Doctor.Stopbox.SG.commands.Cmd.Help;
import com.Doctor.Stopbox.SG.commands.Cmd.SetLobby;
import com.Doctor.Stopbox.SG.commands.Cmd.SgCommand;
import com.Doctor.Stopbox.SG.commands.Cmd.SpawnPoints;
import com.Doctor.Stopbox.SG.commands.Cmd.Start;
import com.Doctor.Stopbox.SG.commands.Cmd.centerPoint;
import com.Doctor.Stopbox.SG.events.BlockEvents;
import com.Doctor.Stopbox.SG.events.DisquiseSync;
import com.Doctor.Stopbox.SG.events.FleshClick;
import com.Doctor.Stopbox.SG.events.PlayerDamage;
import com.Doctor.Stopbox.SG.events.PlayerDeath;
import com.Doctor.Stopbox.SG.events.PlayerJoin;
import com.Doctor.Stopbox.SG.events.PlayerLeave;
import com.Doctor.Stopbox.SG.events.PlayerMove;
import com.Doctor.Stopbox.SG.events.SpecClick;
import com.Doctor.Stopbox.SG.util.ChatUtil;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;
import com.Doctor.Stopbox.SG.util.Game;
import com.Doctor.Stopbox.SG.util.Vector3D;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

public class SurvivalGames extends JavaPlugin {

	
	public static boolean noDamage = true;
	File locations;
	File chats;

	protected static FileConfiguration locs;
	protected static FileConfiguration chat;

	public static HashMap<Player, Player> mutations = new HashMap<Player, Player>();
	
	private ProtocolManager protocolManager;
	public static List<Player> players = new ArrayList<Player>();

	PluginDescriptionFile pdfFile = getDescription();
	
	@Override
	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	@Override
	public void onEnable() {
		ChatUtil.sendMessageToConsole("Plugin loading up..." + pdfFile.getName().toString() + " v." + pdfFile.getVersion().toString());
		ChatUtil.sendMessageToConsole("Loading Events and Commands. " + pdfFile.getName().toString() + " v." + pdfFile.getVersion().toString());
		this.registerEvents();
		this.registerCommands();
		Game.joinLobby();
		saveDefaultConfig();
		ChatUtil.sendMessageToConsole("Configs Loading up... " + pdfFile.getName().toString() + " v." + pdfFile.getVersion().toString());
		locations = new File(this.getDataFolder(), "locations.yml");
		locations = new File(this.getDataFolder(), "chat.yml");

		locs = YamlConfiguration.loadConfiguration(locations);
		chat = YamlConfiguration.loadConfiguration(chats);

		saveLocationConfigs();
		saveChatConfigs();

		ChatUtil.sendMessageToConsole("Configs Loaded up... " + pdfFile.getName().toString() + " v." + pdfFile.getVersion().toString());

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (!ConnectionHandler.getMutations().isEmpty()) {
					ConnectionHandler.syncDisquiseRotation();
				}
			}
		}, 0L, 1L);
		protocolManager
		.getAsynchronousManager()
		.registerAsyncHandler(
				new PacketAdapter(this,
						PacketType.Play.Client.ARM_ANIMATION) {

					@Override
					public void onPacketReceiving(PacketEvent event) {
						final int ATTACK_REACH = 4;
						int animationType = event.getPacket()
								.getIntegers().read(1);
						// Only consider swing arm animation
						if (animationType != 1) {
							return;
						}
						Player observer = event.getPlayer();
						Location observerPos = observer
								.getEyeLocation();
						Vector3D observerDir = new Vector3D(observerPos
								.getDirection());
						Vector3D observerStart = new Vector3D(
								observerPos);
						Vector3D observerEnd = observerStart
								.add(observerDir.multiply(ATTACK_REACH));
						Player hit = null;
						// Get nearby entities
						for (Player target : protocolManager
								.getEntityTrackers(observer)) {
							// No need to simulate an attack if the
							// player is already visible
							if (!observer.canSee(target)) {
								// Bounding box of the given player
								Vector3D targetPos = new Vector3D(
										target.getLocation());
								Vector3D minimum = targetPos.add(-0.5,
										0, -0.5);
								Vector3D maximum = targetPos.add(0.5,
										1.67, 0.5);
								if (hasIntersection(observerStart,
										observerEnd, minimum, maximum)) {
									if (hit == null
											|| hit.getLocation()
											.distanceSquared(
													observerPos) > target
													.getLocation()
													.distanceSquared(	
															observerPos)) {
										hit = target;
										
									}
								}
							}
						}
						// Simulate a hit against the closest player
						if (hit != null && hit.getNoDamageTicks() == 0) {
							PacketContainer useEntity = protocolManager.createPacket(PacketType.Play.Client.USE_ENTITY, false);
							useEntity.getIntegers().write(0, hit.getEntityId());
							useEntity.getEntityUseActions().write(0, EntityUseAction.ATTACK);
							ConnectionHandler.attackSound(hit);
							// Chance of breaking the visibility
							try {
								protocolManager.recieveClientPacket(
										event.getPlayer(), useEntity);							
								} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					// Get entity trackers is not thread safe
				}).syncStart();

	}

	private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min,
			Vector3D max) {
		final double epsilon = 0.0001f;
		Vector3D d = p2.subtract(p1).multiply(0.5);
		Vector3D e = max.subtract(min).multiply(0.5);
		Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
		Vector3D ad = d.abs();

		if (Math.abs(c.x) > e.x + ad.x)
			return false;
		if (Math.abs(c.y) > e.y + ad.y)
			return false;
		if (Math.abs(c.z) > e.z + ad.z)
			return false;
		if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
			return false;
		if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
			return false;
		if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
			return false;
		return true;
	}

	public FileConfiguration getLocs() {
		return locs;
	}
	public FileConfiguration getChat() {
		return chat;
	}
	public void saveLocationConfig() {
		try {
			locs.save(locations);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveChatConfig() {
		try {
			chat.save(chats);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new FleshClick(), this);
		pm.registerEvents(new DisquiseSync(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerDamage(), this);
		pm.registerEvents(new BlockEvents(), this);
		pm.registerEvents(new SpecClick(), this);

	}

	public void registerCommands() {

		CommandHandler handler = new CommandHandler();

		handler.register("sg", new SgCommand());
		//Main commands
		handler.register("centerpoint", new centerPoint());
		handler.register("start", new Start());
		handler.register("setlobby", new SetLobby());
		handler.register("nextspawn", new SpawnPoints());
		handler.register("enable", new Enable());
		handler.register("disable", new Disable());
		handler.register("help", new Help());

		getCommand("sg").setExecutor(handler);
	}

	public void saveLocationConfigs() {
		if (!locations.exists()) {
			saveResource("locations.yml", false);
		}
	}
	public void saveChatConfigs() {
		if (!chats.exists()) {
			saveResource("chatss.yml", false);
		}
	}
}
// (Chest Crates/ Map voting/ Lobby/ Join signs/ everything about it is awesome)

// Chest Crates --> Messaages
// Map voting --> You know,
// Join Signs --> Like a custom TeleportSigns
// Customizable messages
// mutations