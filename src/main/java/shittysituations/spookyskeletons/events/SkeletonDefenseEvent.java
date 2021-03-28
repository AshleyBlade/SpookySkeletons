package shittysituations.spookyskeletons.events;

import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class SkeletonDefenseEvent implements Listener {

    ArrayList<Skeleton> skeletonArr = SkeletonSpawnEvent.skeletonArr;

    // Small event handler to set damage dealt to Boss Skeletons to 1/4 -> makes them harder without increasing health.
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Skeleton)) return; // if the hit entity isn't a skeleton return
        Skeleton skeleton = (Skeleton) event.getEntity(); // store the hit entity as a skeleton
        if(!skeletonArr.contains(skeleton)) return; // if the skeleton array does not contain this skeleton return
        event.setDamage(event.getDamage() * 0.25); // set the damage of the event to 1/4 of full damage
    }
}
