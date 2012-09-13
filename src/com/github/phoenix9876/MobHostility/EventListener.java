package com.github.phoenix9876.MobHostility;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EventListener implements Listener
{
	private static MobHostility plugin;
	
	public EventListener(MobHostility main)
	{
		EventListener.plugin = main;
		main.getServer().getPluginManager().registerEvents(this,main);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTargetChanged(EntityTargetEvent event)
	{
		// Force mobs to remember players with flagged armor and/or items
		if(event.getReason() == TargetReason.FORGOT_TARGET) {
			if(event.getTarget() instanceof Player)
			{
				Player p = (Player) event.getTarget();
				if(plugin.checkPlayerInventoryForForbiddenItemsByEntityType(p,event.getEntityType().toString().toLowerCase(),"hostile"))
				{
					event.setCancelled(true);
				}
			}
		}
	}
}
