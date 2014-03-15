package com.gmail.cjmiller721.VIPPack.Commands;

import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cjmiller721.ServerPack.Main;

public class PlayerWeather implements CommandExecutor{


	@Override
	public boolean onCommand(CommandSender arg0, Command cmd, String arg1, String[] arg2) {
		if(arg0 instanceof Player && arg0.hasPermission(Main.PERM_WEATHER)){
			if(arg2.length < 1){
				arg0.sendMessage(ChatColor.RED + "/pweather [reset|set] (clear|rain)");
				return true;
			}
			if(arg2[0].equalsIgnoreCase("reset")){
				((Player)arg0).resetPlayerWeather();
				return true;
			}else if(arg2[0].equalsIgnoreCase("set")){
				if(arg2.length < 2) {
					arg0.sendMessage(ChatColor.RED + "Must give a type of weather to set too.");
					return true;
				}else{
					if(arg2[1].equalsIgnoreCase("clear")){
						((Player)arg0).setPlayerWeather(WeatherType.CLEAR);
					}else if(arg2[1].equalsIgnoreCase("rain")){
						((Player)arg0).setPlayerWeather(WeatherType.DOWNFALL);
					}else{
						arg0.sendMessage(ChatColor.RED + "Type of weather must be \"clear\" or \"rain\"");
						return true;
					}
				}
			}
		}

		return false;
	}
	
}
