package me.emresmrlp.fakelobbyes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.emresmrlp.fakelobbyes.command.FakeLobbyCommand;
import me.emresmrlp.fakelobbyes.command.LobbyCommand;
import me.emresmrlp.fakelobbyes.listener.BlockListener;
import me.emresmrlp.fakelobbyes.listener.ChatCommandListener;
import me.emresmrlp.fakelobbyes.listener.DamageDropListener;
import me.emresmrlp.fakelobbyes.listener.JoinQuitListener;
import me.emresmrlp.fakelobbyes.listener.WorldListener;
import me.emresmrlp.fakelobbyes.util.UpdateChecker;

public class FakeLobbyES extends JavaPlugin implements PluginMessageListener{
	
	public static FakeLobbyES instance;
	
	ProtocolManager protocolManager;
	
	List<String> forbidden = new ArrayList<String>();
	
	private File settingsFile = new File(this.getDataFolder(), "settings.yml");
	private YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
	
	private File messagesFile = new File(this.getDataFolder(), "messages.yml");
	private YamlConfiguration messages = YamlConfiguration.loadConfiguration(messagesFile);
	
	public void onEnable() {
		instance = this;
		
		if(!getDataFolder().exists()) getDataFolder().mkdirs();
		
		if(!settingsFile.exists()) this.saveResource("settings.yml", false);
		if(!messagesFile.exists()) this.saveResource("messages.yml", false);
		
		reloadPluginFolder();
		
		getCommand("fakelobby").setExecutor(new FakeLobbyCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatCommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
		
		Bukkit.getLogger().info("[FakeLobbyES] Plugin actived.");
		Bukkit.getLogger().info("[FakeLobbyES] Developed by emresmrlp. (forestedge#5714)");
		Bukkit.getLogger().info("");

		if(getSettings().getBoolean("settings.update-check")) {
			if(UpdateChecker.check()) {
				Bukkit.getLogger().info("[FakeLobbyES] Plugin is up to date! " + this.getDescription().getVersion());
			}else {
				Bukkit.getLogger().warning("[FakeLobbyES] Update available! https://www.spigotmc.org/resources/fakelobbyes-useful-fakelobby-plugin.78241/");
			}
		}else {
			Bukkit.getLogger().warning("[FakeLobbyES] Update checker is disabled!");
		}
		new BukkitRunnable() {
			@Override
		    public void run() {
		    	for(World w: Bukkit.getWorlds()) {
		    		w.setTime(5000L);
		    	}
		    }
		}.runTaskTimer(this, 0L, 3000L);

		
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		this.protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
						if (!event.getPlayer().hasPermission("fakelobbyes.admin")) event.setCancelled(true);
				}
			}
		});
	}

	public static void reloadPluginFolder() {
		try {
			instance.settings.load(instance.settingsFile);
			instance.messages.load(instance.messagesFile);
			instance.settings.save(instance.settingsFile);
			instance.messages.save(instance.messagesFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void savePluginFolder() {
		try {
			instance.settings.save(instance.settingsFile);
			instance.settings.load(instance.settingsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static YamlConfiguration getSettings() {
			return instance.settings;
	}
	public static String getMessages(String key) {
		return setColors(instance.messages.getString(key));
	}
	public static String setColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	@Override
	public void onPluginMessageReceived(String s, Player p, byte[] b) {}
}
