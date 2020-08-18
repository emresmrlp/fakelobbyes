package me.emresmrlp.fakelobbyes.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.xephi.authme.events.LoginEvent;
import me.emresmrlp.fakelobbyes.FakeLobbyES;
import me.emresmrlp.fakelobbyes.util.ConnectionUtil;
import me.emresmrlp.fakelobbyes.util.LocationUtil;

public class JoinQuitListener implements Listener {
	@EventHandler
	public void onLogin(LoginEvent e) {
		if(FakeLobbyES.getSettings().getBoolean("timer.enabled")) {
			ConnectionUtil.connectDuration(e.getPlayer());
		}else {
			ConnectionUtil.connect(e.getPlayer());
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(FakeLobbyES.getSettings().getBoolean("settings.join-invisibility")) {
			for (Player allPlayers : Bukkit.getOnlinePlayers()) {
				e.getPlayer().hidePlayer(allPlayers);
				allPlayers.hidePlayer(e.getPlayer());
			}
		}
		if(FakeLobbyES.getSettings().getBoolean("joinquitmessage.enabled") == true) {
			e.setJoinMessage(FakeLobbyES.setColors(FakeLobbyES.getSettings().getString("joinquitmessage.join").replace("%player%", e.getPlayer().getName())));
		}else {
			e.setJoinMessage(null);
		}
		if(e.getPlayer().hasPermission("fakelobbyes.admin")) {
			e.getPlayer().setPlayerListName(FakeLobbyES.setColors(FakeLobbyES.getSettings().getString("nametag.op").replace("%player%", e.getPlayer().getName())));
		}else {
			e.getPlayer().setPlayerListName(FakeLobbyES.setColors(FakeLobbyES.getSettings().getString("nametag.player").replace("%player%", e.getPlayer().getName())));
		}
		if(FakeLobbyES.getSettings().getBoolean("scoreboard.enabled") == true) {
			ScoreboardManager m = Bukkit.getScoreboardManager();
	        Scoreboard b = m.getNewScoreboard();
	       
	        Objective o = b.registerNewObjective("Blue", "");
	        o.setDisplaySlot(DisplaySlot.SIDEBAR);
	        o.setDisplayName(FakeLobbyES.setColors(FakeLobbyES.getSettings().getString("scoreboard.title")));
	        List<String> ls = FakeLobbyES.getSettings().getStringList("scoreboard.lines");
	        Integer l1 = ls.size();
	        for(String sc : ls) {
	        	o.getScore(FakeLobbyES.setColors(sc).replace("%player%", e.getPlayer().getName())).setScore(l1);
	        	l1--;
	        }
	        e.getPlayer().setScoreboard(b);
		}
		if(FakeLobbyES.getSettings().getString("spawnloc.world") != null) {
			LocationUtil.tpSpawn(e.getPlayer());
		}else {
			Bukkit.getLogger().warning("[FakeLobby] Spawn not found. /fakelobby setspawn");
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(FakeLobbyES.getSettings().getBoolean("joinquitmessage.enabled") == true) {
			e.setQuitMessage(FakeLobbyES.setColors((FakeLobbyES.getSettings().getString("joinquitmessage.quit")
					.replace("%player%", e.getPlayer().getName()))));
		}else {
			e.setQuitMessage(null);
		}
	}
}
