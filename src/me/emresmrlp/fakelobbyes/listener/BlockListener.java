package me.emresmrlp.fakelobbyes.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class BlockListener implements Listener{
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!(e.getPlayer().hasPermission("fakelobbyes.admin"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(FakeLobbyES.getMessages("noUse"));
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!(e.getPlayer().hasPermission("fakelobbyes.admin"))) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(FakeLobbyES.getMessages("noUse"));
		}
	}
}
