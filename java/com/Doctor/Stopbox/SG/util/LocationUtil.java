package com.Doctor.Stopbox.SG.util;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.data.PlayerData;

public class LocationUtil {

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");

	public static void teleportToSpawn(Player p) {
		Location loc = new Location(
				Bukkit.getWorld(plugin.getLocs().getString("world.world")),
				plugin.getLocs().getDouble("map.lobby.X"), 
				plugin.getLocs().getDouble("map.lobby.Y"), 
				plugin.getLocs().getDouble("map.lobby.Z"));
		p.teleport(loc);

	}
	public static void teleportAllToSpawn(){
		for(Player p : Bukkit.getOnlinePlayers())
			teleportToSpawn(p);
	}


	public static void teleportToCenter(Player p){
		Location loc = new Location(
				Bukkit.getWorld(plugin.getLocs().getString("world.world")),
				plugin.getLocs().getDouble("world.center.X"), 
				plugin.getLocs().getDouble("world.center.Y"), 
				plugin.getLocs().getDouble("world.center.Z"));
		p.teleport(loc);
	}

	Location locs;
	static int i = 1;
	static int delay = 0;
	static double x;
	static double y;
	static double z;
	public static void teleportToPedistals(){
		for(final Player p : Bukkit.getOnlinePlayers()) {
			delay = delay + 10;
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

				@Override
				public void run() {
					x = plugin.getLocs().getDouble("map.spawn "+ i +".X");
					y = plugin.getLocs().getDouble("map.spawn " + i + ".Y");
					z = plugin.getLocs().getDouble("map.spawn " + i + ".Z");
					delay = delay - 10;

					p.teleport(new Location(p.getWorld(), x, y, z));
					PlayerData pd = new PlayerData(p, i);
					Game.getPlayerData().put(p, pd);
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1000, 2);
					i++;

				}
			}, delay);
		}

	}
	public static void teleportToPedistals(final Player p, final int is){
				x = plugin.getLocs().getDouble("map.spawn "+ is +".X");
				y = plugin.getLocs().getDouble("map.spawn " + is + ".Y");
				z = plugin.getLocs().getDouble("map.spawn " + is + ".Z");
				p.teleport(new Location(p.getWorld(), x, y, z));
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1000, 2);
				i++;
	}
}



