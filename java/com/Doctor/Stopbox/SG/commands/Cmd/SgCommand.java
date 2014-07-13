package com.Doctor.Stopbox.SG.commands.Cmd;

//Imports for the base command class.
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.commands.CommandInterface;

//This class implements the Command Interface.
public class SgCommand implements CommandInterface
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		Player p = (Player) sender;
		p.sendMessage(ChatColor.RED + "Please do /sg help for help");
		return false;
	}

}