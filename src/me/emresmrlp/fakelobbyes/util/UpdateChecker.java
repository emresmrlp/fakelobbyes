package me.emresmrlp.fakelobbyes.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;

import me.emresmrlp.fakelobbyes.FakeLobbyES;

public class UpdateChecker {
	public static boolean check() {
        try {
			InputStream data = new URL("https://api.spigotmc.org/legacy/update.php?resource=78241").openStream();
			BufferedReader req = new BufferedReader(new InputStreamReader(data));
			if(req.readLine() == FakeLobbyES.instance.getDescription().getVersion()) {
				return true;
			}else {
				return false;
			}
		} catch (IOException e) {
			Bukkit.getLogger().warning("[FakeLobbyES] Updater not working! (forestedge#5714)");
			return false;
		}
    }
}
