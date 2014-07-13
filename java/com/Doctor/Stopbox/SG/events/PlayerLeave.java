package com.Doctor.Stopbox.SG.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;

public class PlayerLeave implements Listener{


	@EventHandler
	public void onJoin(PlayerQuitEvent e){
		SurvivalGames.players.remove(e.getPlayer());
		if(ConnectionHandler.getMutations().containsKey(e.getPlayer().getUniqueId())){
			ConnectionHandler.setMutation(e.getPlayer(), false, true);
		}
		e.setQuitMessage(ChatColor.GREEN + e.getPlayer().getName() + " has Left!! " + (Bukkit.getOnlinePlayers().length-1) + "/24");
	}
}