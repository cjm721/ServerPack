package com.gmail.cjmiller721.ServerPack.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.gmail.cjmiller721.ServerPack.Main;

public class PlayerTime implements CommandExecutor{


	@Override
	public boolean onCommand(CommandSender arg0, Command cmd, String arg1, String[] arg2) {
		if(arg0 instanceof Player && arg0.hasPermission(Main.PERM_TIME)){
			if(arg2.length < 1){
				arg0.sendMessage(ChatColor.RED + "/ptime [reset|set] (time) (true|false)");
				return true;
			}
			if(arg2[0].equalsIgnoreCase("reset")){
				((Player)arg0).resetPlayerTime();
				return true;
			}else if(arg2[0].equalsIgnoreCase("set")){
				if(arg2.length < 3) {
					arg0.sendMessage(ChatColor.RED + "Must give a unit of time and if it should be relative or not.");
					return true;
				}else{
					try{
						long time = Long.parseLong(arg2[1]);
						boolean relative = Boolean.parseBoolean(arg2[2]);
						((Player)arg0).setPlayerTime(time, relative);
						return true;
					}catch(NumberFormatException e){
						arg0.sendMessage(ChatColor.RED + "Must give a unit of time also.");
						return true;
					}
				}
			}
		}

		return false;
	}


}
