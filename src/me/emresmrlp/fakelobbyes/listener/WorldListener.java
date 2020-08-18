package me.emresmrlp.fakelobbyes.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener{
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onThunder(ThunderChangeEvent e) {
		if(e.toThunderState()) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
}
