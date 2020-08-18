package me.emresmrlp.fakelobbyes.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.emresmrlp.fakelobbyes.util.ConnectionUtil;

public class LobbyCommand implements CommandExecutor{
	public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
		if (cs instanceof Player) {
			ConnectionUtil.connect((Player) cs);
		}else {
			Bukkit.getLogger().warning("[FakeLobbyES] No command from console.");
		}
		return false;
	}
}
