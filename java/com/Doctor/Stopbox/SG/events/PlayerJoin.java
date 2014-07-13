package com.Doctor.Stopbox.SG.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.util.ConnectionHandler;
import com.Doctor.Stopbox.SG.util.Game;

public class PlayerJoin implements Listener{
	

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		ConnectionHandler.sendPlayers();
		SurvivalGames.players.add(e.getPlayer());
		e.setJoinMessage(ChatColor.AQUA + e.getPlayer().getName() + " has joined!! " + Bukkit.getOnlinePlayers().length + "/24");
	}
	
}
