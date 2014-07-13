package com.Doctor.Stopbox.SG.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Doctor.Stopbox.SG.util.GameState;



public class PlayerMove implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(!(e.getFrom().getBlockZ() == e.getTo().getBlockZ()) || !(e.getFrom().getBlockX() == e.getTo().getBlockX())){
			if(GameState.isState(GameState.WARMUP)){
				e.setTo(e.getFrom());
			}
		}

	}
}
