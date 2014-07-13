package com.Doctor.Stopbox.SG.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.SurvivalGames;

public class ChatUtil {

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");


	private static String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chatName").replace("'", ""));


	private static Server server = Bukkit.getServer();
	private static ConsoleCommandSender console = server.getConsoleSender();

	public static void broadcast(String msg){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.sendMessage(starter() + " " + msg);
		}

	}

	
	//Returns Prefix from Config
	private static String starter(){
		return prefix;
	}

	
	public static void sendMessageToConsole(String msg){
		console.sendMessage(starter() + " " + ChatColor.RED + msg);
	}

	public static void sendMessage(Player player, String message){
		player.sendMessage(starter() + " " + message);
	}


}