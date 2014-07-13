package com.Doctor.Stopbox.SG.events;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;
import com.Doctor.Stopbox.SG.util.Game;
import com.Doctor.Stopbox.SG.util.LocationUtil;

public class PlayerDeath implements Listener {
	
	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");

	
	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		if(!ConnectionHandler.getMutations().containsKey(e.getEntity().getUniqueId())) {
			if(!Game.getPlayerData().get(e.getEntity()).getMutated() && e.getEntity().getKiller() instanceof Player){
				SurvivalGames.mutations.put(e.getEntity(), e.getEntity().getKiller());
				ItemStack mutate = new ItemStack(Material.ROTTEN_FLESH);
				ItemMeta im = mutate.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Mutate!");
				im.setLore(Arrays.asList("Gain revenge against your enemy"));
				mutate.setItemMeta(im);
				e.getEntity().getInventory().setItem(8, mutate);
			}
			
			ItemStack spec = new ItemStack(Material.NETHER_STAR);
			ItemMeta is = spec.getItemMeta();
			is.setDisplayName(ChatColor.GREEN + "Spectate");
			spec.setItemMeta(is);
			e.getEntity().getInventory().setItem(0, spec);
			
			e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.WITHER_DEATH, 2000, 0);
			e.getEntity().setHealth(20.0);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					LocationUtil.teleportToSpawn(e.getEntity());
				}
			}, 2L);
		}
	}
	
}
