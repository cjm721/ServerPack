package com.gmail.cjmiller721.ServerPack.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.cjmiller721.ServerPack.Main;
import com.gmail.cjmiller721.ServerPack.Listeners.Teleport;
import com.gmail.cjmiller721.ServerPack.Teleport.PlayerConfig;
import com.gmail.cjmiller721.ServerPack.Teleport.Request;
import com.gmail.cjmiller721.ServerPack.Teleport.Request.Type;

public class Tpa implements CommandExecutor{

	HashMap<String, Request> requests = new HashMap<String, Request>();

	@Override
	public boolean onCommand(CommandSender arg0, Command cmd, String arg1, String[] arg2) {
		if(arg0 instanceof ConsoleCommandSender){
			arg0.sendMessage("Must be a player");
			return true;
		}

		if(!arg0.hasPermission(Main.PERM_TPA)){
			arg0.sendMessage("You do not have permission to use this command.");
		}
		if(arg1.equalsIgnoreCase("tpaccept")){
			Request request = requests.get(arg0.getName());
			if(request == null){
				arg0.sendMessage(ChatColor.RED + "You have no pending teleports");
				return true;
			}
			if( (System.currentTimeMillis() - request.sendTime)/1E3 > Main.plugin.getConfig().getInt("TPATimeout",10) ){
				arg0.sendMessage(ChatColor.RED + "Teleport had timedout.");
				return true;
			}
			if(Main.plugin.getServer().getPlayer(request.fromUsername) == null){
				arg0.sendMessage(ChatColor.RED + request.fromUsername + " is no longer online.");
				return true;
			}

			PlayerConfig toSave = null;
			switch(request.type){
			case TPA:
				Player b = Main.plugin.getServer().getPlayer(arg0.getName());
				Player a = Main.plugin.getServer().getPlayer(request.fromUsername);
				a.teleport(b);

				b.sendMessage(ChatColor.GOLD + a.getName() + " has teleported to you.");

				toSave = Teleport.players.get(request.fromUsername);
				toSave.lastTeleport = System.currentTimeMillis();
				break;
			case TPAHERE:
				b = Main.plugin.getServer().getPlayer(arg0.getName());
				a = Main.plugin.getServer().getPlayer(request.fromUsername);
				b.teleport(a);

				a.sendMessage(ChatColor.GOLD + b.getName() + " has teleported to you.");
				toSave = Teleport.players.get(request.toUsername);
				toSave.lastTeleport = System.currentTimeMillis();
				break;
			}
			requests.remove(arg0.getName());
			toSave.saveConfig();
			return true;
		}

		PlayerConfig configA = Teleport.players.get(arg0.getName());

		if((System.currentTimeMillis() - configA.lastTeleport)/1E3 < Main.plugin.getConfig().getInt("TPACoolDown") ){
			int secondsLeft = (int) (Main.plugin.getConfig().getInt("TPACoolDown") - (System.currentTimeMillis() - configA.lastTeleport)/1E3);
			arg0.sendMessage(ChatColor.RED + "You are still on cooldown. " + secondsLeft + " seconds untill you can use it again.");
			return true;
		}



		if(arg2.length < 1){
			arg0.sendMessage(ChatColor.RED + "You must input a person to teleport too.");
			return true;
		}
		if(Main.plugin.getServer().getPlayer(arg2[0]) == null){
			arg0.sendMessage(ChatColor.RED + arg2[0] + " is not online.");
			return true;

		}
		if(!isFriends(arg0.getName(), arg2[0])){
			arg0.sendMessage(ChatColor.RED + "You are not mutual friends.");
			return true;
		}

		PlayerConfig configB = Teleport.players.get(arg0.getName());

		int secondsLeft = (int) (Main.plugin.getConfig().getInt("TPACoolDown") - (System.currentTimeMillis() - configB.lastTeleport)/1E3);
		if(secondsLeft > 0){
			arg0.sendMessage(ChatColor.RED + arg2[0] + " is still on cooldown. " + secondsLeft + " seconds untill " + arg2[0] + " can use it again.");
			return true;
		}

		//Tpa to player
		if(arg1.equalsIgnoreCase("tpa")){
			Main.plugin.getServer().getPlayer(arg2[0]).sendMessage(ChatColor.GOLD + arg0.getName()+ " has requested to teleport to you.");
			requests.put(arg2[0], new Request(arg0.getName(), arg2[0], Type.TPA));
			return true;
		}
		//Teleport player to you.
		else if(arg1.equalsIgnoreCase("tpahere")){
			Main.plugin.getServer().getPlayer(arg2[0]).sendMessage(ChatColor.GOLD + arg0.getName()+ " has requested that you teleport to them.");
			requests.put(arg2[0], new Request(arg0.getName(), arg2[0], Type.TPAHERE));
			return true;
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

}
