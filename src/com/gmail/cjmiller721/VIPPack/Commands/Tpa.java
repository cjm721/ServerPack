package com.gmail.cjmiller721.VIPPack.Commands;

import java.util.Collections;

import net.cubespace.Yamler.Config.InvalidConfigurationException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.gmail.cjmiller721.ServerPack.Main;
import com.gmail.cjmiller721.ServerPack.Listeners.Teleport;
import com.gmail.cjmiller721.ServerPack.Teleport.PlayerConfig;

public class Tpa implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command cmd, String arg1, String[] arg2) {
		if(arg0 instanceof ConsoleCommandSender){
			arg0.sendMessage("Must be a player");
			return true;
		}
		
		if(!arg0.hasPermission(Main.PERM_TPA)){
			arg0.sendMessage("You do not have permission to use this command.");
		}
		long a = System.nanoTime() - Teleport.times.get(arg0.getName());
		if(Teleport.times.get(arg0.getName()) != null &&  )
		
		if(arg2.length < 1 || !isFriends(arg0.getName(), arg2[0])){
			arg0.sendMessage(ChatColor.RED + "You are not mutuial friends.");
			return true;
		}
			
		//Tpa to player
		if(arg1.equalsIgnoreCase("tpa")){
			Main.plugin.getServer().getPlayer(arg2[0]).sendMessage(ChatColor.GOLD + arg0.getName()+ " has requested to teleport to you.");
			Teleport.times.put(arg0.getName(), System.nanoTime());
		}
		//Teleport player to you.
		else if(arg1.equalsIgnoreCase("tpahere")){
			
		}
		
		return false;
	}
	
	
	private boolean isFriends(String a, String b){
		PlayerConfig pA = Teleport.players.get(a);
		PlayerConfig pB = Teleport.players.get(a);
		
		if(pA == null || pB == null)
			return false;
		if(pA.friends.contains(b) && pB.friends.contains(a))
			return true;
		
		return false;
	}
	
	private void saveConfig(PlayerConfig config){
		try {
		    config.save();
		} catch(InvalidConfigurationException ex) {
		    System.out.println("Error in saving the Config YML");
		    ex.printStackTrace();
		}
	}
}
