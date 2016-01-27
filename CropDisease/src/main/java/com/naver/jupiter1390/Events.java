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
		Material mat = block.getType(); // �̺�Ʈ�� �߻��� ���� �������ڵ� (Material)
		
		// config�� Types ���� ����
		ConfigurationSection cs = config.getConfigurationSection("Types");
		
		/* config�� Types. �Ʒ��� ��� Ű��
		 * 
		 * ����)
		 * Types:
		 *   A: 1
		 *   B: 2
		 *   C: 3
		 *   
		 *   ���⼭ Types �Ʒ��� Ű��: A, B, C
		 *   
		 *   cs.getKeys(bool) ���� bool��
		 *   Types�� A, B, C ������ ���Ե� �Ʒ� ����� ��(1, 2, 3)������ �� ���ؿð�����
		 *   ���������� ���� ����
		 */
		Set<String> types = cs.getKeys(false);
		
		// Types �Ʒ��� ��� Ű��(A, B, C)�� for������ ���� t�� ����
		for(String t : types) {
			
			/* Ű���� Material Enum������ ��ȯ
			 * ���⼭ Material Enum�̶� Material�� ��ũ����
			 * ��� �������̳� ���� �������ڵ带 ����
			 * �̰� Enum �̶�°����� ���ǵǾ� ����
			 */
			Material type = Material.getMaterial(t);
			
			// �̺�Ʈ�� �߻��� ���� ������ config.yml�� ���ǵǾ��ִ�
			// ������ ���ԵǾ� ������
			if(mat.equals(type)) {
				
				double c = config.getInt("Types." + t);
				double c1 = Math.random();
				
				if(c1 > c) {
					
					Location loc = block.getLocation();
					
					// DEBUG ����� �޼���
					plugin.getLogger().info("CropGrowEvent on world " + loc.getWorld()
							+ ", X" + loc.getBlockX() + ", Y" + loc.getBlockY()+", Z" + loc.getBlockZ());
					
					// �Ʒ��� ���� ���� (��ǥ��ġ �Ʒ������� �� 3�� �Ʒ�����)
					for(int i=1;i<4;i++) {
						
						Block b = loc.getWorld().getBlockAt(loc.add(0, -i, 0));
						
						if(!b.getType().equals(type)) {
							
							// ���� �ص��� ���������� ����
							loc.add(0, 1, 0).getBlock().setType(Material.DEAD_BUSH);
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}