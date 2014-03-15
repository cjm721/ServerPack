package com.gmail.cjmiller721.ServerPack.Commands;

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

		PlayerConfig config = Teleport.players.get(arg0.getName());
		
		if((System.nanoTime() - config.lastTeleport)/1E9 < Main.plugin.getConfig().getInt("TPACoolDown") ){
			int secondsLeft = (int) (Main.plugin.getConfig().getInt("TPACoolDown") - (System.nanoTime() - Teleport.requestTimes.get(arg0.getName()))/1E9);
			arg0.sendMessage(ChatColor.RED + "You are still on cooldown. " + secondsLeft + " seconds untill you can use it again.");
			return true;
		}

		
		if(arg2.length < 1 || !isFriends(arg0.getName(), arg2[0])){
			arg0.sendMessage(ChatColor.RED + "You are not mutual friends.");
			return true;
		}
		
		if(Teleport.requestTimes.get(arg2[0]) != null && (System.nanoTime() - Teleport.requestTimes.get(arg2[0]))/1E9 < Main.plugin.getConfig().getInt("TPACoolDown") ){
			int secondsLeft = (int) (Main.plugin.getConfig().getInt("TPACoolDown") - (System.nanoTime() - Teleport.requestTimes.get(arg2[0]))/1E9);
			arg0.sendMessage(ChatColor.RED + arg2[0] + " is still on cooldown. " + secondsLeft + " seconds untill " + arg2[0] + " can use it again.");
			return true;
		}
			
		//Tpa to player
		if(arg1.equalsIgnoreCase("tpa")){
			Main.plugin.getServer().getPlayer(arg2[0]).sendMessage(ChatColor.GOLD + arg0.getName()+ " has requested to teleport to you.");
			Teleport.requestTimes.put(arg2[0], System.nanoTime());
		}
		//Teleport player to you.
		else if(arg1.equalsIgnoreCase("tpahere")){
			
		}else if(arg1.equalsIgnoreCase("tpaccept")){
			
		}
		
		return false;
	}
	
	
	private boolean isFriends(String a, String b){
		PlayerConfig pA = Teleport.players.get(a);
		PlayerConfig pB = Teleport.players.get(a);
		
		if(pA == null || pB == null)
			return false;
		if(pA.getFriends().contains(b) && pB.getFriends().contains(a))
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
