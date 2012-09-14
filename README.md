#MobHostility v1.0

Plugin for Bukkit to control mob targeting.

##Configuration

_All values except hostileradius, passiveradius, and time must be typed in [] brackets!_

> worldtypes

Description: A list of all world types in which to modify mob targeting
Values: overworld, nether, end, all, none
NOTE: The "none" option disables the plugin!
This field can be left empty if a valid worldname is provided.

> worldnames

Description: A list of all world types in which to modify mob targeting
Values: Case-sensitive world names on the server, i.e. world, world_nether, world_the_end...
This field can be left empty if a valid worldtype is provided.

> mobitems

Description: A list of mobs with associated items that will cause the mob to attack or ignore players with these items in their inventory
Format: mobname:
          hostile: []
            helmets: []
            chestplates: []
            leggings: []
            boots: []
            items: []
          passive:
            helmets: []
            chestplates: []
            leggings: []
            boots: []
            items: []
Valid mob names: zombie, skeleton, spider, cave_spider, creeper, slime, silverfish, enderman, ender_dragon, pig_zombie, blaze, ghast, magma_cube, giant, all
Valid items: Any Item ID or Item ID with damage values, such as "262" for arrows or "5:2" for a specific wooden plank
NOTE: You can use any Item ID, including those from mods like IndustrialCraft2, RedPower2, BuildCraft 2/3, etc.

> hostileradius

Description: Number of blocks around the player to modify mobs to attack the player
Must be greater than 0.

> passiveradius

Description: Number of blocks around the player to modify mobs to ignore the player
NOTE: This feature is not currently enabled. Watch for a future update!

> time

Description: Time in seconds between server checks on player inventories. Default is 5 seconds (100 server ticks).
Must be 1 or greater