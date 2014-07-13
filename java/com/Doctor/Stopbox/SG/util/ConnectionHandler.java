package com.Doctor.Stopbox.SG.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import net.minecraft.server.v1_7_R3.DataWatcher;
import net.minecraft.server.v1_7_R3.MathHelper;
import net.minecraft.server.v1_7_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_7_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R3.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_7_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.Doctor.Stopbox.SG.SurvivalGames;

public class ConnectionHandler {

	private static HashMap<UUID, Integer> par1 = new HashMap<UUID, Integer>();
	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");
	
	
	public static HashMap<UUID, Integer> getMutations() {
		return par1;
	}

	public static void sendPlayers() {
		for (UUID uuid : par1.keySet()) {
		Player p = Bukkit.getPlayer(uuid);
		Location loc = p.getLocation();
		PacketPlayOutSpawnEntityLiving packet = null;
		packet = new PacketPlayOutSpawnEntityLiving();
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(p.getEntityId());
		PacketPlayOutEntityDestroy destroy2 = new PacketPlayOutEntityDestroy(par1.get(uuid));
		for (Player op : Bukkit.getOnlinePlayers()) {
			 if (op != p) {
			((CraftPlayer) op).getHandle().playerConnection
					.sendPacket(destroy);
			((CraftPlayer) op).getHandle().playerConnection
			.sendPacket(destroy2);
			 }
		}
		try {
			Field a = packet.getClass().getDeclaredField("a");
			Random rand = new Random();
			int id = rand.nextInt();
			a.setAccessible(true);
			a.setInt(packet, id);
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.setInt(packet, EntityType.PIG_ZOMBIE.getTypeId());
			Field c = packet.getClass().getDeclaredField("c");
			c.setAccessible(true);
			c.setInt(packet, p.getLocation().getBlockX() * 32);
			Field d = packet.getClass().getDeclaredField("d");
			d.setAccessible(true);
			d.setInt(packet, p.getLocation().getBlockY() * 32);
			Field e = packet.getClass().getDeclaredField("e");
			e.setAccessible(true);
			e.setInt(packet, p.getLocation().getBlockZ() * 32);
			Field i = packet.getClass().getDeclaredField("i");
			i.setAccessible(true);
			i.setByte(packet, (byte) 0);
			Field j = packet.getClass().getDeclaredField("j");
			j.setAccessible(true);
			j.setByte(packet, (byte) 0);
			Field k = packet.getClass().getDeclaredField("k");
			k.setAccessible(true);
			k.setByte(packet, (byte) 0);
			Field f = packet.getClass().getDeclaredField("f");
			f.setAccessible(true);
			f.setInt(packet, 0);
			Field g = packet.getClass().getDeclaredField("g");
			g.setAccessible(true);
			g.setInt(packet, 0);
			Field h = packet.getClass().getDeclaredField("h");
			h.setAccessible(true);
			h.setInt(packet, 0);
			Field l = packet.getClass().getDeclaredField("l");
			l.setAccessible(true);
			DataWatcher watcher = new DataWatcher(null);
			watcher.a(0, (Object) (byte) 0);
			watcher.a(1, (Object) (short) 0);
			watcher.a(8, (Object) (byte) 0);
			l.set(packet, watcher);
			par1.put(p.getUniqueId(), id);
			for (Player op : Bukkit.getOnlinePlayers()) {
				 if (op != p) {
				((CraftPlayer) op).getHandle().playerConnection
						.sendPacket(packet);
				 }
			}
			int x = (int) MathHelper.floor(32D * loc.getX());
			int y = (int) MathHelper.floor(32D * loc.getY());
			int z = (int) MathHelper.floor(32D * loc.getZ());
			PacketPlayOutEntityTeleport locfix = new PacketPlayOutEntityTeleport(
					id, x, y, z, getCompressedAngle(loc.getYaw()),
					getCompressedAngle(loc.getPitch()));
			for (Player op : Bukkit.getOnlinePlayers()) {
				 if (op != p) {
				((CraftPlayer) op).getHandle().playerConnection
						.sendPacket(locfix);
				op.hidePlayer(p);
				 }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setMutation(Player p, boolean flag, boolean logingoff) {
		if (flag) {
			Location loc = p.getLocation();
			PacketPlayOutSpawnEntityLiving packet = null;
			packet = new PacketPlayOutSpawnEntityLiving();
			PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(p.getEntityId());
			for (Player op : Bukkit.getOnlinePlayers()) {
				 if (op != p) {
				((CraftPlayer) op).getHandle().playerConnection
						.sendPacket(destroy);
				 }
			}
			try {
				Field a = packet.getClass().getDeclaredField("a");
				Random rand = new Random();
				int id = rand.nextInt();
				a.setAccessible(true);
				a.setInt(packet, id);
				Field b = packet.getClass().getDeclaredField("b");
				b.setAccessible(true);
				b.setInt(packet, EntityType.PIG_ZOMBIE.getTypeId());
				Field c = packet.getClass().getDeclaredField("c");
				c.setAccessible(true);
				c.setInt(packet, p.getLocation().getBlockX() * 32);
				Field d = packet.getClass().getDeclaredField("d");
				d.setAccessible(true);
				d.setInt(packet, p.getLocation().getBlockY() * 32);
				Field e = packet.getClass().getDeclaredField("e");
				e.setAccessible(true);
				e.setInt(packet, p.getLocation().getBlockZ() * 32);
				Field i = packet.getClass().getDeclaredField("i");
				i.setAccessible(true);
				i.setByte(packet, (byte) 0);
				Field j = packet.getClass().getDeclaredField("j");
				j.setAccessible(true);
				j.setByte(packet, (byte) 0);
				Field k = packet.getClass().getDeclaredField("k");
				k.setAccessible(true);
				k.setByte(packet, (byte) 0);
				Field f = packet.getClass().getDeclaredField("f");
				f.setAccessible(true);
				f.setInt(packet, 0);
				Field g = packet.getClass().getDeclaredField("g");
				g.setAccessible(true);
				g.setInt(packet, 0);
				Field h = packet.getClass().getDeclaredField("h");
				h.setAccessible(true);
				h.setInt(packet, 0);
				Field l = packet.getClass().getDeclaredField("l");
				l.setAccessible(true);
				DataWatcher watcher = new DataWatcher(null);
				watcher.a(0, (Object) (byte) 0);
				watcher.a(1, (Object) (short) 0);
				watcher.a(8, (Object) (byte) 0);
				l.set(packet, watcher);
				par1.put(p.getUniqueId(), id);
				for (Player op : Bukkit.getOnlinePlayers()) {
					 if (op != p) {
					((CraftPlayer) op).getHandle().playerConnection
							.sendPacket(packet);
					 }
				}
				int x = (int) MathHelper.floor(32D * loc.getX());
				int y = (int) MathHelper.floor(32D * loc.getY());
				int z = (int) MathHelper.floor(32D * loc.getZ());
				PacketPlayOutEntityTeleport locfix = new PacketPlayOutEntityTeleport(
						id, x, y, z, getCompressedAngle(loc.getYaw()),
						getCompressedAngle(loc.getPitch()));
				for (Player op : Bukkit.getOnlinePlayers()) {
					 if (op != p) {
					((CraftPlayer) op).getHandle().playerConnection
							.sendPacket(locfix);
					op.hidePlayer(p);
					 }
					 syncItemInHand(p, p.getItemInHand());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (!flag) {
			PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(par1.get(p.getUniqueId()));
			PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle());
			for (Player op : Bukkit.getOnlinePlayers()) {
				 if (op != p) {
				((CraftPlayer) op).getHandle().playerConnection
						.sendPacket(destroy);
				if (!logingoff) {
				((CraftPlayer) op).getHandle().playerConnection
				.sendPacket(spawn);
				}
				 }
			}
			par1.remove(p.getUniqueId());
		}
	}

	private static byte getCompressedAngle(float value) {
		return (byte) ((value * 256.0F) / 360.0F);
	}

	public static void syncDisquiseRotation() {
		for (UUID uuid : par1.keySet()) {
			PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
			try {
				Field a = packet.getClass().getDeclaredField("a");
				a.setAccessible(true);
				a.set(packet, par1.get(uuid));
				Field b = packet.getClass().getDeclaredField("b");
				b.setAccessible(true);
				b.set(packet, getCompressedAngle(Bukkit.getPlayer(uuid)
						.getLocation().getYaw()));
				for (Player op : Bukkit.getOnlinePlayers()) {
					 if (op != Bukkit.getPlayer(uuid)) {
					((CraftPlayer) op).getHandle().playerConnection
							.sendPacket(packet);
					 }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void syncItemInHand(Player p, ItemStack Item) {
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(par1.get(p.getUniqueId()), 0, CraftItemStack.asNMSCopy(Item));
		for (Player op : Bukkit.getOnlinePlayers()) {
			 if (op != p) {
			((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet);
			 }
		}
	}

	public static void syncDisquiseMovement(Player p, Location from, Location to) {
		float[] movement = getMovement(from, to);
		PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutRelEntityMoveLook(
				par1.get(p.getUniqueId()), (byte) movement[0],
				(byte) movement[1], (byte) movement[2],
				getCompressedAngle(to.getYaw()),
				getCompressedAngle(to.getPitch()));
		for (Player op : Bukkit.getOnlinePlayers()) {
			 if (op != p) {
			((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet);
			 }
		}
	}

	public static void syncVector(Player p, Vector v) {
		PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(
				par1.get(p.getUniqueId()), v.getX(), v.getY(), v.getZ());
		for (Player op : Bukkit.getOnlinePlayers()) {
			 if (op != p) {
			((CraftPlayer) op).getHandle().playerConnection.sendPacket(packet);
			 }
		}
	}
	
	public static void animateDisquise(Player p, int animationid) {
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		 try {
			 Field a = packet.getClass().getDeclaredField("a");
			 a.setAccessible(true);
			 a.set(packet, par1.get(p.getUniqueId()));
			 Field b = packet.getClass().getDeclaredField("b");
			 b.setAccessible(true);
			 b.set(packet, animationid);
				for (Player op : Bukkit.getOnlinePlayers()) {
					 if (op != p) {
					((CraftPlayer) op).getHandle().playerConnection
							.sendPacket(packet);
					 }
				}
			 } catch (Exception e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			 }
	}
	
	public static void fixLocation(Player p) {
		Location loc = p.getLocation();
		int x = (int) MathHelper.floor(32D * loc.getX());
		int y = (int) MathHelper.floor(32D * loc.getY());
		int z = (int) MathHelper.floor(32D * loc.getZ());
		PacketPlayOutEntityTeleport locfix = new PacketPlayOutEntityTeleport(
				par1.get(p.getUniqueId()), x, y, z, getCompressedAngle(loc.getYaw()),
				getCompressedAngle(loc.getPitch()));
		for (Player op : Bukkit.getOnlinePlayers()) {
			 if (op != p) {
			((CraftPlayer) op).getHandle().playerConnection
					.sendPacket(locfix);
			 }
		}
	}

	private static float[] getMovement(Location from, Location to) {
		int x = MathHelper.floor(to.getX() * 32D);
		int y = MathHelper.floor(to.getY() * 32D);
		int z = MathHelper.floor(to.getZ() * 32D);
		int xf = MathHelper.floor(from.getX() * 32D);
		int yf = MathHelper.floor(from.getY() * 32D);
		int zf = MathHelper.floor(from.getZ() * 32D);
		int diffx = x - xf;
		int diffy = y - yf;
		int diffz = z - zf;
		float[] returnvalues = new float[3];
		returnvalues[0] = diffx;
		returnvalues[1] = diffy;
		returnvalues[2] = diffz;
		return returnvalues;
		// return new MovementValues(diffx, diffy, diffz,
		// getCompressedAngle(to.getYaw()), getCompressedAngle(to.getPitch()));
	}
	
	public static void handleBed(final Player p, final Location loc) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				((CraftPlayer)p).getHandle().a((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
			}
		}, 10L);
	}

	public static void attackSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.ZOMBIE_PIG_HURT, 1L, 1L);
	}
}
