package com.Doctor.Stopbox.SG.commands.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.commands.CommandInterface;
import com.Doctor.Stopbox.SG.util.ChatUtil;
import com.Doctor.Stopbox.SG.util.Game;

public class Start implements CommandInterface{

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;

		
		if(p.hasPermission("sg.commands.start") || p.hasPermission("sg.commands.admin")){
			ChatUtil.sendMessage(p, "Game Starting...");
			Game.start();
			Bukkit.getScheduler().cancelTask(Game.taskID);
		}
		return false;
	}

	
}
