package com.naver.jupiter1390;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
	
	private Plugin plugin;
	
	public Events(Plugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onCropGrow(BlockGrowEvent event) {
		
		FileConfiguration config = plugin.getConfig();
		Block block = event.getBlock();
		Material mat = block.getType(); // 이벤트가 발생된 블럭의 아이템코드 (Material)
		ConfigurationSection cs = config.getConfigurationSection("Types");
		
		// config의 작물 종류 얻어오기
		Set<String> types = cs.getKeys(false);
		
		for(String t : types) {
			
			/* 작물 키값을 Material Enum값으로 변환
			 * 여기서 Material Enum이란 Material은 마크상의
			 * 모든 아이템이나 블럭의 아이템코드를 뜻함
			 * 이게 Enum 이라는것으로 정의되어 있음
			 * 자세한건 Material 을 직접 확인해보기
			 */
			Material type = Material.getMaterial(t);
			
			// 이벤트가 발생된 블럭의 재질이 config.yml에 정의되어있는
			// 재질에 포함되어 있을떄
			if(type != null && mat.equals(type)) {
				
				// 해당 작물에 정의된 이름
				String alias = config.getString("Types." + t);
				double c = config.getInt("Chance." + alias); // 해당 작물의 질병확률
				double c1 = Math.random();
				
				if(c1 > c) {
					
					Location loc = block.getLocation();
					
					// DEBUG 디버그 메세지
					plugin.getLogger().info("CropGrowEvent on world " + loc.getWorld()
							+ ", X" + loc.getBlockX() + ", Y" + loc.getBlockY()+", Z" + loc.getBlockZ());
					
					// 아래블럭 감지 루프 (목표위치 아래블럭부터 그 3블럭 아래까지)
					for(int i=1;i<4;i++) {
						
						// y축 i블럭 아래에 있는 블럭
						Block b = loc.getWorld().getBlockAt(loc.add(0, -i, 0));
						
						// b 블럭이 작물블럭이면 조건문 안이 실행됨
						if(!b.getType().equals(type)) {
							
							// 가장 밑둥의 작물을 변경
							loc.add(0, 1, 0).getBlock().setType(Material.DEAD_BUSH);
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}