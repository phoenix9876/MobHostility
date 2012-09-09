package com.github.phoenix9876.MobHostility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MobHostility extends JavaPlugin
{
	private boolean ValidConfig = false;
	private String ConfigErrorMessage = "";
	
    @Override
    public void onEnable()
    {
    	getLogger().info("Enabling MobHostility...");
    	
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
    	getLogger().info("Disabling MobHostility...");
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
			if(getConfig().getStringList("worldtypes").contains(w.getEnvironment().toString()) || getConfig().getStringList("worldnames").contains(w.getName()))
			{
				for(Player p : w.getPlayers())
				{
					for(String EntityConfigPath : getConfig().getKeys(false))
					{
						
					}
				}
			}
		}
    }
}
