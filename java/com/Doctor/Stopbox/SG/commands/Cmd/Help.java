package com.Doctor.Stopbox.SG.commands.Cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.commands.CommandInterface;
import com.Doctor.Stopbox.SG.util.ChatUtil;

public class Help implements CommandInterface{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;

		ChatUtil.sendMessage(p, "Commands for SurvivalGames");
		ChatUtil.sendMessage(p, ChatColor.DARK_RED + "Users:");
	    p.sendMessage(" ");
		ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg vote ------ Vote for the arena map");
		ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg leave ------ Return to hub");

		if(p.hasPermission("sg.commands.admin")){
			ChatUtil.sendMessage(p, ChatColor.DARK_RED + "Admins");
			ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg enable ------ Enables Starting countdown");
			ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg disable ------ Disables Starting (Good for building/setting spawns)");
			p.sendMessage(" ");
			ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg centerpoint ------ Sets Center point of the map, Where the crate will drop");
			ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg setlobby ------ Sets spawnpoint on Joining and on death");
			ChatUtil.sendMessage(p, ChatColor.GRAY + "/sg nextspawn ------ Sets up to a maximum of 24 spawnpoints for the spawn pedistals");
		}
		return false;
	}


}
