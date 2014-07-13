package com.Doctor.Stopbox.SG.commands.Cmd;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.commands.CommandInterface;
import com.Doctor.Stopbox.SG.util.ChatUtil;
import com.Doctor.Stopbox.SG.util.GameState;

public class Disable implements CommandInterface
{

	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		
		
		if(p.hasPermission("sg.commands.admin") || p.hasPermission("disable")){
			GameState.setState(GameState.DISABLED);
			ChatUtil.sendMessage(p, "Disabled!");
		}
		return false;
	}

}