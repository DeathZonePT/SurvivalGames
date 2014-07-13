package com.Doctor.Stopbox.SG.commands.Cmd;

//Imports Needed.
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.commands.CommandInterface;
import com.Doctor.Stopbox.SG.util.ChatUtil;

//ArgsCmd also implements CommandInterface
public class centerPoint implements CommandInterface
{

	
	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		Location l = p.getLocation();

		
		if(p.hasPermission("sg.commands.admin") || p.hasPermission("sg.commands.centerpoint")){
			plugin.getLocs().set("world.world", l.getWorld().getName());
			double x = l.getBlockX();
			double y = l.getBlockY();
			double z = l.getBlockZ();
			x += 0.5;
			y += 0.5;
			z += 0.5;
			plugin.getLocs().set("map.center.X", x);
			plugin.getLocs().set("map.center.Y", y);
			plugin.getLocs().set("map.center.Z", z);
			ChatUtil.sendMessage(p, "Center Point set at x:" + x + " y:" + y + " z:" + z);
			plugin.saveLocationConfig();
		}
		return false;
	}

}
