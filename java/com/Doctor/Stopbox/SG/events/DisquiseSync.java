package com.Doctor.Stopbox.SG.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;

public class DisquiseSync implements Listener {

	ArrayList<Player> player = new ArrayList<Player>();
	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (ConnectionHandler.getMutations().containsKey(e.getPlayer().getUniqueId())) {
			ConnectionHandler.syncDisquiseMovement(e.getPlayer(), e.getFrom(), e.getTo());
		}
	}

	@EventHandler
	public void onAnimateArm(PlayerAnimationEvent e) {
		if (ConnectionHandler.getMutations().containsKey(e.getPlayer().getUniqueId()) && e.getAnimationType() == PlayerAnimationType.ARM_SWING) {
			ConnectionHandler.animateDisquise(e.getPlayer(), 0);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (ConnectionHandler.getMutations().containsKey(p.getUniqueId())) {
				ConnectionHandler.animateDisquise(p, 1);
				if(e.getDamage() > 4.0D){
					e.setDamage(4.0D);
				}
			}
		}
	}

	@EventHandler
	public void onHeal(EntityRegainHealthEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(ConnectionHandler.getMutations().containsKey(p.getUniqueId())){
				p.setSaturation(20F);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		if (ConnectionHandler.getMutations().containsKey(e.getPlayer().getUniqueId())) {
			player.add(e.getPlayer());
			ConnectionHandler.setMutation(e.getPlayer(), false, false);
		}
	}

	@EventHandler
	public void updateItem(PlayerItemHeldEvent e) {
		if (ConnectionHandler.getMutations().containsKey(e.getPlayer().getUniqueId())) {
			ConnectionHandler.syncItemInHand(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (ConnectionHandler.getMutations().containsKey(e.getEntity().getUniqueId())) {
			ConnectionHandler.setMutation(e.getEntity(), false, false);
			e.getEntity().getInventory().clear();
			for(PotionEffect pot : e.getEntity().getActivePotionEffects()){
				e.getEntity().removePotionEffect(pot.getType());
			}
			for (Player op : Bukkit.getOnlinePlayers()) {
				if (op != e.getEntity()) {
					op.showPlayer(e.getEntity());
				}
			}
		}
	}

	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent e) {
		if (player.contains(e.getPlayer())) {
			ConnectionHandler.setMutation(e.getPlayer(), true, false);
			player.remove(e.getPlayer());
		}
	}
}
