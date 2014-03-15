package com.gmail.cjmiller721.ServerPack.Listeners;

import java.util.HashMap;

import net.cubespace.Yamler.Config.InvalidConfigurationException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.cjmiller721.ServerPack.Teleport.PlayerConfig;

public class Teleport implements Listener{

	public static HashMap<String, PlayerConfig> players;
	public static HashMap<String, Long> times;
	
	public Teleport(){
		Teleport.players = new HashMap<String, PlayerConfig>();
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		PlayerConfig config;
		
		try{
			config = new PlayerConfig(e.getPlayer().getName());
			config.init();
			players.put(e.getPlayer().getName(), config);
		} catch(InvalidConfigurationException ex) {
		    System.out.println(e.getPlayer().getName() + " Config YML was wrong");
		    ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e){
		players.remove(e.getPlayer().getName());
	}
}
