package com.naver.jupiter1390;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CropDisease extends JavaPlugin {
	
	private FileConfiguration config;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		if(getConfig().getBoolean("Enable")) {
			getServer().getPluginManager().registerEvents(new Events(this), this);
		}
		
	}
	
	public void logDebug(String... str) {
		
		Logger log = getLogger();
		
		if(!getConfig().getBoolean("Debug"))
			return;
		
		for(String s : str) {
			
			log.info(s);
			
		}
		
	}

	public void reloadConfiguration() {
		this.config = getConfig();
	}
	
	public FileConfiguration getConfiguration() {
		return this.config;
	}
	
}