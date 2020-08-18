package me.emresmrlp.fakelobbyes.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class ConnectionUtil {
	public static void connectDuration(Player player) {
		int duration = FakeLobbyES.getSettings().getInt("timer.duration");
		player.sendMessage(FakeLobbyES.getMessages("timerstart").replace("%duration%", String.valueOf(duration)));
		Bukkit.getServer().getScheduler().runTaskLater(FakeLobbyES.instance, new Runnable(){
			@Override
			public void run() {
        		player.sendMessage(FakeLobbyES.getMessages("timerfinish"));
        		connect(player);
            }
		}, duration*20);
	}
	public static void connect(Player player) {
		if(FakeLobbyES.getSettings().getBoolean("connection.connected") == true) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
	        DataOutputStream out = new DataOutputStream(b);
	        try {
	            out.writeUTF("Connect");
	            out.writeUTF(FakeLobbyES.getSettings().getString("connection.goto"));
	            Bukkit.getServer().getScheduler().runTaskLater(FakeLobbyES.instance, new Runnable(){
	                public void run() {
	                	player.sendMessage(FakeLobbyES.getMessages("noconnection"));
	                }
	            }, 100L);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        player.sendPluginMessage(FakeLobbyES.instance, "BungeeCord", b.toByteArray());
		}else{
			Bukkit.getLogger().warning("[FakeLobbyES] No connected (settings.yml).");
		}
	}
}
