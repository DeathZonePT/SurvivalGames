package com.Doctor.Stopbox.SG.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ChatUtil;
import com.Doctor.Stopbox.SG.util.SpecUtil;

public class SpecClick implements Listener{

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");


	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		if (
				(e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && e.getAction() == Action.RIGHT_CLICK_AIR)
				|| (e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && e.getAction() == Action.RIGHT_CLICK_BLOCK)){
			ChatUtil.sendMessage(e.getPlayer(), "You are now a spectator");
			SpecUtil.specPlayer(e.getPlayer());
		}

	}
}