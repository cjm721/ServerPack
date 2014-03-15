package com.gmail.cjmiller721.ServerPack.Teleport;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.gmail.cjmiller721.ServerPack.Main;

import net.cubespace.Yamler.Config.Config;

public class PlayerConfig extends Config implements Comparable<PlayerConfig>{


	public PlayerConfig(String player) {
		CONFIG_HEADER = new String[]{"Configuration of the Database for " + player};
		CONFIG_FILE = new File(Main.plugin.getDataFolder(), "\\players\\" + player + ".yml");
		this.username = player;
	}
	
	public long lastTeleport = 0;
	
	public String username = "temp";
	
	private Set<String> friends = new HashSet<String>();
	
	public boolean addFriend(String username){
		return friends.add(username);
	}
	
	public Set<String> getFriends(){
		return friends;
	}
	
	public boolean removeFriend(String username){
		return friends.remove(username);
	}

	@Override
	public int compareTo(PlayerConfig o) {
		return this.username.compareTo(o.username);
	}
}
