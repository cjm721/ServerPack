package com.gmail.cjmiller721.ServerPack.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.cjmiller721.ServerPack.Main;

public class Mystcraft implements Listener{

	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getTypeId() == Main.plugin.getConfig().getInt("Mystcraft.BinderID") 
				&& !(event.getPlayer().hasPermission(Main.PERM_BINDER))){
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED +"Ask an Admin to create a world. Minimum of three people required.");
		}
	}
}
