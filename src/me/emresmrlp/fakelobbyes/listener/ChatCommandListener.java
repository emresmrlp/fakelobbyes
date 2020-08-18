package me.emresmrlp.fakelobbyes.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class ChatCommandListener implements Listener{
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		if(e.getPlayer().hasPermission("fakelobbyes.admin")) {
			Bukkit.broadcastMessage(FakeLobbyES.setColors(FakeLobbyES.getSettings().getString("chat.format"))
					.replace("%player%", e.getPlayer().getName()) + e.getMessage());
		}else {
			e.getPlayer().sendMessage(FakeLobbyES.getMessages("nochat"));
		}
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(!(e.getPlayer().hasPermission("fakelobbyes.admin"))) {
			Boolean d = false;
			List<String> cl = FakeLobbyES.getSettings().getStringList("chat.allowedcommands");
	        for (String cl1 : cl) {
				if (e.getMessage().toLowerCase().startsWith("/" + cl1)) {
				    d = true;
				}
	        }
			if(!d){
				e.setCancelled(true);
				e.getPlayer().sendMessage(FakeLobbyES.getMessages("nocommand"));
			}
		}
	}
}
