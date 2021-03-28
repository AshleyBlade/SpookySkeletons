package shittysituations.spookyskeletons.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

public class SkeletonSpawnEvent implements Listener {

    // store rare spawn skeletons -> add effects on damage event?
    public static final ArrayList<Skeleton> skeletonArr = new ArrayList<>();

    public static Skeleton spawnSpecialSkeleton(Location playerLocation, World playerWorld) {
        Skeleton skeleton = (Skeleton) playerWorld.spawnEntity(playerLocation, EntityType.SKELETON);
        skeleton.setCustomName("Boss Skeleton"); // Name the skeleton
        skeleton.setCustomNameVisible(true);
        Bukkit.broadcastMessage(ChatColor.AQUA + "A Boss Skeleton has been spawned at: " + playerLocation.getX() + ", "
                + playerLocation.getY() + ", " +  playerLocation.getZ());
        skeletonArr.add(skeleton);
        return skeleton;
    }


    @EventHandler
    public void onSkeletonSpawn(EntitySpawnEvent event){
        if(!event.getEntity().getType().equals(EntityType.SKELETON)) return; // if not a skeleton return

        Random random = new Random();
        if(!(random.nextInt(1000) + 1 == 1)) return;

        Skeleton skeleton = (Skeleton) event.getEntity(); // Store the skeleton

        // Broadcast to all players
        Bukkit.broadcastMessage(ChatColor.AQUA + "A Boss Skeleton has emerged! Chunks " + "[" + skeleton.getLocation()
                .getChunk().getX() + ", " + skeleton.getLocation().getChunk().getZ() + "]");
        // Show coords in console
        getLogger().info("Boss Skeleton spawned at: " + skeleton.getLocation());

        skeleton.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "Boss Skeleton"); // Name the skeleton
        if(!skeleton.isCustomNameVisible()) // check if the name is visible
            skeleton.setCustomNameVisible(true); // if it isn't make it visible

        skeletonArr.add(skeleton); // add the skeleton to the arraylist
    }
}