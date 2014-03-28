package com.gmail.cjmiller721.ServerPack.Listeners;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiAFK implements Listener{

	public static HashMap<String, Long> playerTimes;
	
	public AntiAFK(){
		AntiAFK.playerTimes = new HashMap<String, Long>();
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void playerInteractEvent(PlayerInteractEvent event){
		if(event.getPlayer() == null || event.getPlayer().getName() == null || event.getPlayer().getName().isEmpty())
			return;
		addPlayer(event.getPlayer().getName());
		
	}
	
	public static void removePlayer(String name){
		playerTimes.remove(name);
	}
	
	public static void addPlayer(String name){
		playerTimes.put(name, System.currentTimeMillis());
	}
}
