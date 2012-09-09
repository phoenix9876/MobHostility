package com.github.phoenix9876.MobHostility;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class EventListener implements Listener
{
	
	public EventListener(MobHostility main)
	{
		main.getServer().getPluginManager().registerEvents(this,main);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event)
	{
		// TODO Check if player is wearing flagged armor or has Nether Ores, have mobs target player if so
		if(event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER)
		{
			if(event.getPlayer().getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE)
			{
				
			}
			event.getPlayer().getNearbyEntities(64, 64, 64);
		}
	}
	
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
	{
		// TODO Make a list of all players in the Nether to filter the onPlayerMove players
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInventoryChange(InventoryEvent event)
	{
		// TODO Check if player is wearing flagged armor or has Nether Ores, have mobs target player if so or detarget if not (and configured)
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		// TODO Have nether mobs target player if item is Nether Ore
	}
	
	@EventHandler
	public void onEntityForget(EntityTargetEvent event)
	{
		// TODO Force mobs to remember players with flagged armor and/or items
	}
	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event)
	{
		// TODO Add pigmen to global array of all pigmen
	}
}
