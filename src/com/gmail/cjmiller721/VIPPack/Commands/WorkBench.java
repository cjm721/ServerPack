package com.gmail.cjmiller721.VIPPack.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cjmiller721.ServerPack.Main;

public class WorkBench implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command cmd, String arg1, String[] arg2) {
		if(arg0 instanceof Player && arg0.hasPermission(Main.PERM_BENCH)){
			((Player)arg0).openWorkbench(null, true);
			return true;
		}
		return false;
	}

}
