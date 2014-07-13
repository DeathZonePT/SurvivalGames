package com.Doctor.Stopbox.SG.events;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.Doctor.Stopbox.SG.util.GameState;

public class BlockEvents implements Listener{

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(!GameState.isState(GameState.DISABLED)){
			e.setCancelled(true);
		}
	}
		
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		if(e.getBlock().getType().equals(Material.TNT)){
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, Material.TNT);
			e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
		}
		else if(e.getBlock().getType().equals(Material.CAKE)){
			e.getBlock().setType(Material.AIR);
			e.getBlock().setType(Material.CAKE);

		}
		else if(GameState.isState(GameState.DISABLED)){
			e.setCancelled(false);
		}
		else{
			e.setCancelled(true);
		}
	}
}
