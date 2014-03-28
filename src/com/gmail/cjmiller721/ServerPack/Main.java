package com.gmail.cjmiller721.ServerPack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.cjmiller721.ServerPack.Commands.Friend;
import com.gmail.cjmiller721.ServerPack.Commands.PlayerTime;
import com.gmail.cjmiller721.ServerPack.Commands.PlayerWeather;
import com.gmail.cjmiller721.ServerPack.Commands.Tpa;
import com.gmail.cjmiller721.ServerPack.Commands.WorkBench;
import com.gmail.cjmiller721.ServerPack.Listeners.AntiAFK;
import com.gmail.cjmiller721.ServerPack.Listeners.MainListener;
import com.gmail.cjmiller721.ServerPack.Listeners.Mystcraft;
import com.gmail.cjmiller721.ServerPack.Listeners.Teleport;

public class Main extends JavaPlugin {

	public static final String PERM_TIME = "serverPack.time";
	public static final String PERM_WEATHER = "serverPack.weather";
	public static final String PERM_BENCH = "serverPack.bench";
	public static final String PERM_BINDER = "serverPack.binder";
	public static final String PERM_PVPCOMMAND = "serverPack.pvpCommand";
	public static final String PERM_TPA = "serverPack.tpa";

	public static long kickTime;
	
	public static Main plugin;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		Main.plugin = this;

		//VIP Perks
		getCommand("ptime").setExecutor(new PlayerTime());
		getCommand("pweather").setExecutor(new PlayerWeather());
		getCommand("workbench").setExecutor(new WorkBench());



		if(getConfig().getBoolean("MystcraftEnabled", true)){
			getServer().getPluginManager().registerEvents(new Mystcraft(), this);
			System.out.println("GroupMystcraft Enabled");
		}else{
			System.out.println("GroupMystcraft has been skiped");
		}

		//Anti PVP Log
		if(getConfig().getBoolean("AntiPVPEnabled", true)){
			getServer().getPluginManager().registerEvents(new MainListener(), this);
			System.out.println("AntiPVP has been enabled");
		}else{
			System.out.println("AntiPVP has been skipped");
		}


		if(getConfig().getBoolean("TeleportationEnabled", true)){
			//Friend Teleporation System
			getServer().getPluginManager().registerEvents(new Teleport(), this);

			getCommand("friend").setExecutor(new Friend());
			//Set and allias;
			PluginCommand tpa = getCommand("tpa");
			tpa.setExecutor(new Tpa());
			List<String> list = new ArrayList<String>();
			list.add("tpahere");
			list.add("tpaccept");
			tpa.setAliases(list);

			System.out.println("Teleportation has been enabled.");

		} else {
			System.out.println("Teleportation has been skipped.");
		}



		//AntiAFK
		if(getConfig().getBoolean("AntiAFKEnabled", true)){
			getServer().getPluginManager().registerEvents(new AntiAFK(), this);
			Main.kickTime = getConfig().getLong("AntiAFKKickTime", 600);

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					long currentTime = System.currentTimeMillis();
					for(String name: AntiAFK.playerTimes.keySet()){
						if(AntiAFK.playerTimes.get(name) + (kickTime*1000) < currentTime){
							getServer().getPlayer(name).kickPlayer("AFK time limit.");
						}
					}
				}
			}, 0L, 20L * getConfig().getLong("AntiAFKScanTime", 60));
			
			System.out.println("AntiAFK has been enabled");
		}else{
			System.out.println("AntiAFK has been skipped");
		}
	}

}
