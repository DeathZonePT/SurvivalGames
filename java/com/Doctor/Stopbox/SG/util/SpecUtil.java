package com.Doctor.Stopbox.SG.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpecUtil {

	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("SurvivalGames");
	public static ArrayList<Player> spec = new ArrayList<Player>();


	public static void specPlayer(final Player victom) {
		final ItemStack item = new ItemStack(Material.COMPASS);
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Player Teleporter");	
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				victom.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 1000));
				spec.add(victom);
				victom.setGameMode(GameMode.CREATIVE);
				for (Player op : Bukkit.getOnlinePlayers()) {
					op.hidePlayer(victom);
				}

				int pedistal = Game.getPlayerData().get(victom).getPedistal();
				LocationUtil.teleportToPedistals(victom, pedistal);
			}
		}, 2L);	
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				victom.getInventory().setItem(4, item);
				for (Entity en : victom.getNearbyEntities(200, 200, 200)) {
					if (en instanceof Player) {
						Player p = (Player)en;
						if (!spec.contains(en)) {
							String x = String.valueOf(victom.getLocation().distance(p.getLocation()));
							meta.setDisplayName(
									ChatColor.GRAY + "Nearest Player: " + ChatColor.YELLOW + p.getName() +
									ChatColor.GRAY + " Distance: " +  x + " meters away");
							item.setItemMeta(meta);
							break;
						}
					}				
				}
			}

		}, 0L, 1L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				victom.getInventory().setItem(4, item);
				for (int i = 0; i < 200; i++) {
					List<Entity> entities = victom.getNearbyEntities(i,200,i);
					for (Entity e : entities) {
						if (e.getType().equals(EntityType.PLAYER)) {
							Player p = (Player)e;
							if (!spec.contains(e)) {
								String x = String.valueOf(victom.getLocation().distance(p.getLocation()));
								meta.setDisplayName(
										ChatColor.GRAY + "Nearest Player: " + ChatColor.YELLOW + p.getName() +
										ChatColor.GRAY + " Distance: " +  x.substring(0,5) + " meters away");
								item.setItemMeta(meta);
								break;
							}
						}
					}
				}
			}

		}, 0L, 1L);
	}


}
