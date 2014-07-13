package com.Doctor.Stopbox.SG.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ChatUtil;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;
import com.Doctor.Stopbox.SG.util.Game;
import com.Doctor.Stopbox.SG.util.LocationUtil;

public class FleshClick implements Listener {

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");


	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		if (
				(e.getPlayer().getItemInHand().getType() == Material.ROTTEN_FLESH && e.getAction() == Action.RIGHT_CLICK_AIR && !Game.getPlayerData().get(e.getPlayer()).getMutated() || e.getPlayer().getItemInHand().getType() == Material.ROTTEN_FLESH && e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& !Game.getPlayerData().get(e.getPlayer()).getMutated() && e.getPlayer().getKiller() instanceof Player) {
			Game.getPlayerData().get(e.getPlayer()).setMutated(true);
			e.getPlayer().getInventory().clear();
			BukkitTask task = new BukkitRunnable() {
				int time = 10;
				@Override
				public void run() {
					String player = e.getPlayer().getName();
					String killer = e.getPlayer().getKiller().getName();  
					String message = plugin.getChat().getString("mutated").replace("<player>", player).replace("<killer>", killer);
					if (time > 5) {
						ChatUtil.sendMessage(e.getPlayer(), "You will mutate in " + time);
						time--;
					} else if (time != 0) {
						ChatUtil.broadcast("Player " + e.getPlayer().getName() + " will mutate in " + time + "!");
						time--;
					} else if (time == 0) {
						this.cancel();
						ChatUtil.broadcast(message);
						ConnectionHandler.setMutation(e.getPlayer(), true, false);
						LocationUtil.teleportToPedistals(e.getPlayer(), Game.getPlayerData().get(e.getPlayer()).getPedistal());
						ConnectionHandler.fixLocation(e.getPlayer());
						//YYolo
						ItemStack sword = new ItemStack(Material.GOLD_SWORD);
						ItemMeta im = sword.getItemMeta();
						im.setDisplayName(ChatColor.DARK_RED + "Sword of REVENGE!!!");
						sword.setItemMeta(im);
						sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
						//SWAGGERE
						e.getPlayer().getInventory().clear();
						for(PotionEffect potionEffect : e.getPlayer().getActivePotionEffects()){
							e.getPlayer().removePotionEffect(potionEffect.getType());
						}
						e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 100000, true));
						e.getPlayer().getInventory().addItem(sword);
					}
				}
			}.runTaskTimer(plugin, 20L, 20L); 
		}
	}

}
