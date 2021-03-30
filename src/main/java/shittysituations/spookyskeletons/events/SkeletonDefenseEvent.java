package shittysituations.spookyskeletons.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import shittysituations.spookyskeletons.main;

import java.util.ArrayList;
import java.util.Objects;

public class SkeletonDefenseEvent implements Listener {

    ArrayList<Skeleton> skeletonArr = SkeletonSpawnEvent.skeletonArr;

    main plugin;
    public SkeletonDefenseEvent(main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onSkeletonDamaged(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Skeleton)) return; // if not a skeleton
        if(!(event.getDamager() instanceof Player)) return; // if not player return

        Player player = (Player) event.getDamager(); // Cast damager to Player

        if(player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return; // if the item in hand is air return
        ItemStack item = player.getInventory().getItemInMainHand(); // store the mainhand item

        if(!Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) return; // if item has no meta return
        if(item.equals(item.getItemMeta().getDisplayName().contains("Skeleton Slayer"))){
            NamespacedKey key = new NamespacedKey(plugin, "skeletonslayerlevels"); // store the NamespacedKey for skeleton slayer levels
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if(!container.has(key, PersistentDataType.INTEGER)) return;
            int level = container.get(key, PersistentDataType.INTEGER);
            event.setDamage(event.getDamage() * level + 1);
        }
    }

    // Small event handler to set damage dealt to Boss Skeletons to 1/4 -> makes them harder without increasing health.
    @EventHandler
    public void onBossSkeletonDamaged(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Skeleton)) return; // if the hit entity isn't a skeleton return
        Skeleton skeleton = (Skeleton) event.getEntity(); // store the hit entity as a skeleton
        if(!skeletonArr.contains(skeleton)) return; // if the skeleton array does not contain this skeleton return

        double damageMultiplier = 0.25; // Store 0.25 as a double | Default damage dealt to Boss Skeletons

        if(!(event.getDamager() instanceof Player)) return; // If damager is not a player return
        Player player = (Player) event.getDamager(); // Store the player

        ItemStack item = player.getInventory().getItemInMainHand(); // Store the player's mainhand item
        if(!Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) return; // check if it has a display name
        if(item.equals(item.getItemMeta().getDisplayName().contains("Skeleton Slayer"))){ // check if the item has the name Skeleton Slayer

            NamespacedKey key = new NamespacedKey(plugin, "skeletonslayerlevels"); // store the NamespacedKey for skeleton slayer levelsw
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer(); // Get container

            if(container.has(key, PersistentDataType.INTEGER)){ // check if container has the key

                int level = container.get(key, PersistentDataType.INTEGER); // store the persistent data -> level
                damageMultiplier = damageMultiplier * (level + 1); // 0.25 * (level + 1) = 0.25 * 2 = 0.50

                if(level >= 4) {
                    Objects.requireNonNull(skeleton.getLocation().getWorld()).createExplosion(skeleton.getLocation(), 2, true, false);
                }
            }

        }

        event.setDamage(event.getDamage() * damageMultiplier); // set the damage of the event to 1/4 of full damage
    }
}
