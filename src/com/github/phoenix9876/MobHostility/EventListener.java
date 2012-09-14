package com.github.phoenix9876.MobHostility;

import java.util.List;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityDeathEvent;

public class EventListener implements Listener
{
	private static MobHostility plugin;
	private static double HostileRadius;
	//private static double PassiveRadius;
	
	public EventListener(MobHostility main)
	{
		EventListener.plugin = main;
		EventListener.HostileRadius = main.getConfig().getInt("hostileradius");
		//EventListener.PassiveRadius = main.getConfig().getInt("passiveradius");
		main.getServer().getPluginManager().registerEvents(this,main);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTargetChanged(EntityTargetEvent event)
	{
		// Force mobs to remember players with flagged armor and/or items
		if(event.getTarget() != null)
		{
			if(!(event.getTarget().isDead()))
			{
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
				/*else
				{
					if(event.getTarget() instanceof Player)
					{
						Player p = (Player) event.getTarget();
						if(plugin.checkPlayerInventoryForForbiddenItemsByEntityType(p,event.getEntityType().toString().toLowerCase(),"passive"))
						{
							event.setTarget(null);
						}
					}
				}*/
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player p = (Player) event.getEntity();
			if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent)
			{
				List<Entity> mobs = p.getLastDamageCause().getEntity().getNearbyEntities(HostileRadius, HostileRadius, HostileRadius);
				for(Entity e : mobs)
				{
					if(e instanceof Creature)
					{
						Creature c = (Creature) e;
						if(c.getTarget() == p)
						{
							plugin.removeTargetAndAnger(c);
						}
					}
				}
			}
		}
	}
}
