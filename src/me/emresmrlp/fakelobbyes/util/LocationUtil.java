package me.emresmrlp.fakelobbyes.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class LocationUtil {
	public static void tpSpawn(HumanEntity p) {
		World w = Bukkit.getServer().getWorld(FakeLobbyES.getSettings().getString("spawnloc.world"));
		if(!(w == null)) {
			double x = FakeLobbyES.getSettings().getDouble("spawnloc.x");
			double y = FakeLobbyES.getSettings().getDouble("spawnloc.y");
			double z = FakeLobbyES.getSettings().getDouble("spawnloc.z");
			float yaw = FakeLobbyES.getSettings().getInt("spawnloc.yaw");
			float pitch = FakeLobbyES.getSettings().getInt("spawnloc.pitch");
			p.teleport(new Location(w, x, y, z, yaw, pitch));
		}
	}
}
