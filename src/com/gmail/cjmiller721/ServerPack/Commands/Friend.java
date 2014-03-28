package com.gmail.cjmiller721.ServerPack.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import com.gmail.cjmiller721.ServerPack.Listeners.Teleport;
import com.gmail.cjmiller721.ServerPack.Teleport.PlayerConfig;

public class Friend implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {

		if(arg0 instanceof ConsoleCommandSender)
			return true;
		if(arg3.length < 2)
			return false;

		PlayerConfig config = Teleport.players.get(arg0.getName());
		if(arg3[0].equalsIgnoreCase("add")){
			if(config.addFriend(arg3[1])){
				arg0.sendMessage(ChatColor.GOLD + arg3[1] + " was added as a friend.");
				config.saveConfig();
			}else{
				arg0.sendMessage(ChatColor.RED + arg3[1] + " was already a friend.");
			}
			return true;
		}else if(arg3[0].equalsIgnoreCase("remove")){
			if(config.removeFriend(arg3[1])){
				arg0.sendMessage(ChatColor.GOLD + arg3[1] + " was removed as a friend.");
				config.saveConfig();
			}
			else
				arg0.sendMessage(ChatColor.RED + arg3[1] + " was not a friend.");
			return true;
		}else if(arg3[0].equalsIgnoreCase("list")){
			arg0.sendMessage(ChatColor.GOLD + "Friends: " + config.getFriends());
			return true;
		}

		return false;
	}


}
