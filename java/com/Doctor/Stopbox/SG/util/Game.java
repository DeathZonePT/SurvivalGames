package com.Doctor.Stopbox.SG.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Doctor.Stopbox.SG.SurvivalGames;
import com.Doctor.Stopbox.SG.data.PlayerData;


public class Game {

	private static SurvivalGames plugin = (SurvivalGames) Bukkit.getPluginManager().getPlugin("SurvivalGames");
	public static int taskID;
	private static HashMap<Player, PlayerData> playerData = new HashMap<Player, PlayerData>();
	private static ArrayList<Player> alive = new ArrayList<Player>();



	public static void joinLobby(){
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int time = 100;
			public void run() {
				if (time != 0) {
					time--;
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.setSaturation(20);
						p.setLevel(time);
						if(time < 10){
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1000, 1);
						}
					}

					if (time == 0) {
						if (Bukkit.getOnlinePlayers().length < 2) {

							ChatUtil.broadcast("There are not enough players! restarting lobby mode.");
							time = 100;
						} else {
							for (Player p : Bukkit.getOnlinePlayers()) {
								p.setLevel(0);
							}							
							ChatUtil.broadcast("Enough players");
							Bukkit.getScheduler().cancelTask(taskID);
							start();
						}
					}
				}
			}
		}, 0L, 20L);
	}


	public static void start(){
		GameState.setState(GameState.WARMUP);
		LocationUtil.teleportToPedistals();
		SurvivalGames.noDamage = true;
		countdown();
	}

	public static void stop(){
		GameState.setState(GameState.POST_GAME);
		SurvivalGames.noDamage = true;
	}

	private static void countdown() {
		new BukkitRunnable() {
			int time = 10;
			@Override
			public void run() {
				if (time != 0) {
					time--;
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.setLevel(time);
					}
			} else {
				this.cancel();
				GameState.setState(GameState.IN_GAME);
				SurvivalGames.noDamage = false;

			}
			}
		}.runTaskTimer(plugin, 20L, 20L);

	}


	public static HashMap<Player, PlayerData> getPlayerData(){
		return playerData;
	}

}
