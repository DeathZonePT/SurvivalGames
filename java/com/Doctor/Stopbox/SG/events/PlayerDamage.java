package com.Doctor.Stopbox.SG.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.Doctor.Stopbox.SG.SurvivalGames;

public class PlayerDamage implements Listener{

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			if(SurvivalGames.noDamage){
				e.setCancelled(true);
			}
		}
	}

}
