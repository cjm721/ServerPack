package com.gmail.cjmiller721.ServerPack;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.cjmiller721.ServerPack.Commands.PlayerTime;
import com.gmail.cjmiller721.ServerPack.Commands.PlayerWeather;
import com.gmail.cjmiller721.ServerPack.Commands.WorkBench;
import com.gmail.cjmiller721.ServerPack.Listeners.AntiPVPLog;
import com.gmail.cjmiller721.ServerPack.Listeners.Mystcraft;

public class Main extends JavaPlugin {

	public static final String PERM_TIME = "serverPack.time";
	public static final String PERM_WEATHER = "serverPack.weather";
	public static final String PERM_BENCH = "serverPack.bench";
	public static final String PERM_BINDER = "serverPack.binder";
	public static final String PERM_PVPCOMMAND = "serverPack.pvpCommand";
	public static final String PERM_TPA = "serverPack.tpa";
	
	public static Main plugin;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		Main.plugin = this;
		
		//VIP Perks
		getCommand("ptime").setExecutor(new PlayerTime());
		getCommand("pweather").setExecutor(new PlayerWeather());
		getCommand("workbench").setExecutor(new WorkBench());
		
		if(getConfig().getBoolean("MystcraftEnabled")){
			getServer().getPluginManager().registerEvents(new Mystcraft(), this);
			System.out.println("GroupMystcraft Enabled");
		}else{
			System.out.println("GroupMystcraft has been skiped");
		}

		//Anti PVP Log
		if(getConfig().getBoolean("AntiPVPEnabled")){
			getServer().getPluginManager().registerEvents(new AntiPVPLog(), this);
			System.out.println("AntiPVP has been enabled");
		}else{
			System.out.println("AntiPVP has been skiped");
		}

		//Friend Teleporation System
			
	}
	
}
