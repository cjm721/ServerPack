package com.gmail.cjmiller721.ServerPack.Listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.cjmiller721.ServerPack.Main;

public class MainListener implements Listener{
	public static Map<String, String> Tag = new HashMap<String, String>();
	public static Map<String, Long> Time = new HashMap<String, Long>();

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() == null || event.getDamager() == null)
			return;
		
		//---------------------
		//Anti PVP Log
		//---------------------
		
		//Deffender
		if ((event.getEntity() instanceof Player)) {
			Player p = (Player)event.getEntity();
			Time.put(p.getName(), Long.valueOf(System.currentTimeMillis()));

			if (((String)Tag.get(p.getName())).equals("false"))
			{
				String tag = Main.plugin.getConfig().getString("tag-message");
				p.sendMessage(tag.replace('&', '§'));
			}

			int time = Main.plugin.getConfig().getInt("tag-time");

			if (((String)Tag.get(p.getName())).equals("false")) {
				new TagOff(p, time);
			}

			Tag.put(p.getName(), "true");
		}
		
		//Offender
		if ((event.getDamager() instanceof Player)) {
			Player p = (Player)event.getDamager();
			if(p.getName() == null || Tag.get(p.getName()) == null){
				return;
			}
			Time.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
			if (((String)Tag.get(p.getName())).equals("false")) {
				String tag = Main.plugin.getConfig().getString("tag-message");

				p.sendMessage(tag.replace('&', '§'));
			}
			int time = Main.plugin.getConfig().getInt("tag-time");
			if (((String)Tag.get(p.getName())).equals("false"))
				new TagOff(p, time);
			Tag.put(p.getName(), "true");
		}
	}

	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event)
	{
		
		//Remove tag from AntiPvP log
		if ((Tag.containsKey(event.getPlayer().getName())) && 
				(((String)Tag.get(event.getPlayer().getName())).equals("true"))) {
			event.getPlayer().setHealth(0.0);
			String tag = Main.plugin.getConfig().getString("clog-message");
			tag = tag.replace("&", "§");
			tag = tag.replace("{playername}", event.getPlayer().getName());
			Bukkit.broadcastMessage(tag.replace('&', '§'));
		}
		
		//Remove from AFK check.
		AntiAFK.removePlayer(event.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event)
	{
		
		//AntiPVP add to main list
		Tag.put(event.getPlayer().getName(), "false");
		
		//AntiAFK
		AntiAFK.addPlayer(event.getPlayer().getName());
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onCommandPreprocess(AsyncPlayerChatEvent event){
		if(event.getMessage().charAt(0) != '/' || event.getPlayer().hasPermission(Main.PERM_PVPCOMMAND))
			return;
		
		if(Tag.get(event.getPlayer().getName()).equals("true")){
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You are still in combat.");
		}
	}
	
	
	class TagOff
	implements Runnable
	{
		Player player;

		public TagOff(Player p, long ticks)
		{
			Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, this, ticks * 20L);
			this.player = p;
		}

		public void run()
		{
			String msg = Main.plugin.getConfig().getString("untag-message");
			int tagtime = Main.plugin.getConfig().getInt("tag-time");
			msg.replace("&", "§");
			msg.replace("{playername}", this.player.getName());
			if (((String)Tag.get(this.player.getName())).equals("true"))
				if (System.currentTimeMillis() - ((Long)Time.get(this.player.getName())).longValue() > tagtime * 1000 - 20) {
					this.player.sendMessage(msg.replace('&', '§'));
					Tag.put(this.player.getName(), "false");
				}
				else
				{
					long time = System.currentTimeMillis() / 1000L - ((Long)Time.get(this.player.getName())).longValue() / 1000L;

					new TagOff(this.player, time);
				}
		}
	}
}
