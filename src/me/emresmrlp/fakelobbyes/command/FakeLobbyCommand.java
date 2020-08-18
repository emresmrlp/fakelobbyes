package me.emresmrlp.fakelobbyes.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class FakeLobbyCommand implements CommandExecutor {
	public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
		if (cs instanceof Player) {
			if(args.length == 0) {
				if(cs.hasPermission("fakelobbyes.admin")) {
					cs.sendMessage(FakeLobbyES.setColors("&8&l&m================================="));
					cs.sendMessage("");
					cs.sendMessage(FakeLobbyES.setColors("&3FakeLobbyES " + FakeLobbyES.instance.getDescription().getVersion()));
					cs.sendMessage("");
					cs.sendMessage(FakeLobbyES.setColors("&b/fakelobby setspawn &f» &7Set spawn."));
					cs.sendMessage(FakeLobbyES.setColors("&b/fakelobby reload &f» &7Reload plugin."));
					cs.sendMessage("");
					cs.sendMessage(FakeLobbyES.setColors("&8&l&m================================="));
				}else {
					cs.sendMessage(FakeLobbyES.getMessages("noperm"));
				}
			}else if(args.length >= 0) {
				switch (args[0]) {
					case "setspawn":
						FakeLobbyES.getSettings().set("spawnloc.world", ((Player) cs).getLocation().getWorld().getName());
						FakeLobbyES.getSettings().set("spawnloc.x", ((Player) cs).getLocation().getX());
						FakeLobbyES.getSettings().set("spawnloc.y", ((Player) cs).getLocation().getY());
						FakeLobbyES.getSettings().set("spawnloc.z", ((Player) cs).getLocation().getZ());
						FakeLobbyES.getSettings().set("spawnloc.yaw", ((Player) cs).getLocation().getYaw());
						FakeLobbyES.getSettings().set("spawnloc.pitch", ((Player) cs).getLocation().getPitch());
						try {
							FakeLobbyES.savePluginFolder();
						} catch(Exception e){
				            e.printStackTrace();
				        }
						cs.sendMessage(FakeLobbyES.getMessages("spawnready"));
						return false;
					case "reload":
						try {
							FakeLobbyES.savePluginFolder();
							cs.sendMessage(FakeLobbyES.getMessages("reload"));
						} catch(Exception e){
		                    e.printStackTrace();
		                }
						return false;
					default:
						cs.sendMessage(FakeLobbyES.getMessages("cmdnotfound"));
				}
			}
		}else {
			if(args.length >= 0) {
				if(args[0].contains("reload")) {
					try {
						FakeLobbyES.reloadPluginFolder();
						cs.sendMessage(FakeLobbyES.getMessages("reload"));
					} catch(Exception e){
	                    e.printStackTrace();
	                }
				}
			}
			Bukkit.getLogger().warning("[FakeLobbyES] Reload command only. /fakelobby reload");
		}
		return false;
	}
}
