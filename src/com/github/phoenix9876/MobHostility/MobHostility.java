package com.github.phoenix9876.MobHostility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

public class MobHostility extends JavaPlugin
{
	private boolean ValidConfig = false;
	private String ConfigErrorMessage = "";
	
    @Override
    public void onEnable()
    {
    	
    	this.saveDefaultConfig();
    	CheckConfig();
    	
    	if(this.ValidConfig)
    	{
	    	new EventListener(this);
	    	
	    	getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	    		public void run() {
	    			CheckOnlinePlayers();
	    		}
	    	}, 60L, 100L);	
    	}
    	else
    	{
    		getLogger().info(this.ConfigErrorMessage);
    	}
    }
 
    @Override
    public void onDisable()
    {
    	getServer().getScheduler().cancelTasks(this);
    }
    
    public void CheckConfig()
    {
    	List<String> ValidWorldTypes = new ArrayList<String>();
    	List<String> ValidWorldNames = new ArrayList<String>();
    	
    	ValidWorldTypes.add("overworld");
    	ValidWorldTypes.add("nether");
    	ValidWorldTypes.add("end");
    	ValidWorldTypes.add("all");
    	ValidWorldTypes.add("none");
    	
    	for(World w : getServer().getWorlds())
    	{
    		ValidWorldNames.add(w.getName());
    	}
    	
    	if((getConfig().contains("worldtypes") && ValidWorldTypes.containsAll(getConfig().getStringList("worldtypes"))) || 
    			(getConfig().contains("worldnames") && ValidWorldNames.containsAll(getConfig().getStringList("worldnames"))))
    	{
    		this.ValidConfig = true;
    	}
    	else
    	{
    		this.ConfigErrorMessage = "Invalid world type or name! Disabling MobHostility...";
    	}
    }
    
    public void CheckOnlinePlayers()
    {
    	List<World> Worlds = getServer().getWorlds();
		for(World w : Worlds)
		{
			if((getConfig().getStringList("worldtypes").contains(w.getEnvironment().toString().toLowerCase()) || getConfig().getStringList("worldnames").contains(w.getName())
					|| getConfig().getStringList("worldtypes").contains("all")) && !(getConfig().getStringList("worldtypes").contains("none")))
			{
				for(Player p : w.getPlayers())
				{
					List<Entity> NearbyMobs = p.getNearbyEntities(getConfig().getDouble("hostileradius"), getConfig().getDouble("hostileradius"), getConfig().getDouble("hostileradius"));
					for(Entity e : NearbyMobs)
					{
						if((e instanceof Creature) && (getConfig().getConfigurationSection("mobitems").getKeys(false).contains(e.getType().toString().toLowerCase())))
						{
							Creature c = (Creature) e;
							if(checkPlayerInventoryForForbiddenItemsByEntityType(p,e.getType().toString().toLowerCase(),"hostile"))
							{
								setTargetAndAnger(c,p);
							}
						}
					}
				}
			}
		}
    }
    
    public boolean checkPlayerInventoryForForbiddenItemsByEntityType(Player p, String entitytype, String ControlType)
    {
		boolean ReturnValue = false;
		if((getConfig().getIntegerList("mobitems."+entitytype+"."+ControlType+".helments").contains(p.getInventory().getHelmet().getTypeId()))
				|| (getConfig().getIntegerList("mobitems."+entitytype+"."+ControlType+".chestplates").contains(p.getInventory().getChestplate().getTypeId()))
				|| (getConfig().getIntegerList("mobitems."+entitytype+"."+ControlType+".leggings").contains(p.getInventory().getLeggings().getTypeId()))
				|| (getConfig().getIntegerList("mobitems."+entitytype+"."+ControlType+".boots").contains(p.getInventory().getBoots().getTypeId())))
		{
			ReturnValue = true;
		}
		if(!(getConfig().getIntegerList("mobitems"+entitytype+"."+ControlType+".items").isEmpty()))
		{
			for(int i : getConfig().getIntegerList("mobitems"+entitytype+".items"))
			{
				if(p.getInventory().contains(i))
				{
					ReturnValue = true;
				}
			}
		}
		return ReturnValue;
    }
    
    public void setTargetAndAnger(Creature c, Player p)
    {
		if(c.getType() == EntityType.PIG_ZOMBIE)
		{
			PigZombie pz = (PigZombie) c;
			pz.setAngry(true);
		}
		if(c.getType() == EntityType.WOLF)
		{
			Wolf wolf = (Wolf) c;
			wolf.setAngry(true);
		}
		c.setTarget(p);
    }
    
    public void removeTargetAndAnger(Creature c)
    {
		if(c.getType() == EntityType.PIG_ZOMBIE)
		{
			PigZombie pz = (PigZombie) c;
			pz.setAngry(false);
		}
		if(c.getType() == EntityType.WOLF)
		{
			Wolf wolf = (Wolf) c;
			wolf.setAngry(false);
		}
		c.setTarget(null);
    }
}
