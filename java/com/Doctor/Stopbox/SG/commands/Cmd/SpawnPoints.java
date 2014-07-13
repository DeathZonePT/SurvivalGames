package com.Doctor.Stopbox.SG.commands.Cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.commands.CommandInterface;
import com.Doctor.Stopbox.SG.util.ChatUtil;

public class SpawnPoints implements CommandInterface
{

	int i = 0;
	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		Location l = p.getLocation();

		if(p.hasPermission("sg.commands.admin") || p.hasPermission("sg.commands.setspawns")){
			if(!plugin.getLocs().contains("arena.spawn.number")){
				plugin.getLocs().set("arena.spawn.number", i);
			}
			if(i > 24){
				return false;
			}
			i = plugin.getLocs().getInt("arena.spawn.number");
			i++;
			double x = l.getBlockX();
			double y = l.getBlockY();
			double z = l.getBlockZ();
			x += 0.5;
			y += 0.5;
			z += 0.5;
			plugin.getLocs().set("map.spawn "+ i +".X", x);
			plugin.getLocs().set("map.spawn "+ i +".Y", y);
			plugin.getLocs().set("map.spawn "+ i +".Z", z);
			ChatUtil.sendMessage(p, "Spawn Point " + i +" set at x:" + x + " y:" + y + " z:" + z);
			plugin.getLocs().set("arena.spawn.number", i);
			
			plugin.saveLocationConfig();
		}
		return false;
	}

}