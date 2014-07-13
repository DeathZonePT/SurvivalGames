package com.Doctor.Stopbox.SG.data;

import org.bukkit.entity.Player;

public class PlayerData {

	private Player player;
	private int pednumber;
	private boolean mutated = false;
	private Player killer;
	
	
	public PlayerData(Player player, int pednumber) {
		this.player = player;
		this.pednumber = pednumber;
	}
	
	public boolean getMutated(){
		return mutated;
	}
	public void setMutated(boolean b){
		mutated = b;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getPedistal() {
		return pednumber;
	}
	
	public void setKiller(Player killer) {
		this.killer = killer;
	}
}
