package shittysituations.spookyskeletons.events;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import shittysituations.spookyskeletons.items.HorseMountItem;
import shittysituations.spookyskeletons.items.MeatHookItem;
import shittysituations.spookyskeletons.items.SkeletonSlayerItem;
import shittysituations.spookyskeletons.main;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

public class SkeletonDeathEvent implements Listener {

    ArrayList<Skeleton> skeletonArr = SkeletonSpawnEvent.skeletonArr;
    main plugin;
    public SkeletonDeathEvent(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBossSkeletonDeath(EntityDeathEvent event){
        if(!event.getEntity().getType().equals(EntityType.SKELETON)) return; // skeleton died
        if(event.getEntity().getKiller() == null) return; // died to player

        Skeleton skeleton = (Skeleton) event.getEntity(); // Store skeleton as Skeleton
        if(!skeletonArr.contains(skeleton)) return; // if skeleton wasn't a Boss Skeleton return

        Player killer = skeleton.getKiller(); // store entity that killed skeleton as player
        ItemStack item = killer.getInventory().getItemInMainHand(); // store player's main hand item


        killer.sendMessage("You have successfully killed the skeleton!"); // Send a message to the killer
        giveLootReward(killer, skeleton); // Drop loot at the skeleton
    }

    @EventHandler
    public void onSkeletonDeath(EntityDeathEvent event){
        if(!event.getEntity().getType().equals(EntityType.SKELETON)) return; // skeleton died
        if(event.getEntity().getKiller() == null) return; // died to player
        Skeleton skeleton = (Skeleton) event.getEntity(); // Store skeleton as Skeleton

        Player killer = skeleton.getKiller(); // store entity that killed skeleton as player
        ItemStack item = killer.getInventory().getItemInMainHand(); // store player's main hand item
        if(!Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) return; // check if item doesn't have a display name
        if(item.getItemMeta().getDisplayName().contains("Skeleton Slayer")){ // check if display name contains Skeleton Slayer;
            NamespacedKey killKey = new NamespacedKey(plugin, "skeletonslayerkills"); // Store skeleton slayer kills namespacedkey
            NamespacedKey levelKey = new NamespacedKey(plugin, "skeletonslayerlevels"); // store skeleton slayer levels namespacedkey

            ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore(); // store the item's lore
            ItemMeta itemMeta = item.getItemMeta(); // store the item's meta

            PersistentDataContainer container = itemMeta.getPersistentDataContainer(); // store the item's persistent data container
            if(!container.has(killKey, PersistentDataType.INTEGER)) return; // check if container has kills
            int kills = container.get(killKey, PersistentDataType.INTEGER); // store kills
            int level = kills / 100; // level is equal to kills / 100

            assert lore != null;
            if(lore.size() > 2) // if there are 3 lines of lore
                lore.remove(2); // remove the third line (last)

            if(!(level >= 4)) { // if the level is equal to or greater than 4
                container.set(levelKey, PersistentDataType.INTEGER, level); // set persistent level to level
                container.set(killKey, PersistentDataType.INTEGER, kills + 1); // set kills to kills + 1

                lore.add(ChatColor.GOLD + "Level " + level); // add lore to show the current level
                itemMeta.setLore(lore); // set the lore
                item.setItemMeta(itemMeta); // set the meta
                return; // return;
            }


            lore.add(ChatColor.GOLD + "Max Level"); // Max level achieved
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 5);
            itemMeta.setLore(lore); // set the lore
            item.setItemMeta(itemMeta); // set the meta
        }
    }

    private void giveLootReward(Player killer, Skeleton skeleton) {
        Random random = new Random();
        int chance = random.nextInt(2) + 1;

        ArrayList<ItemStack> loot = new ArrayList<>();

        switch(chance){ // Add more loot latter
            case 1:
                ItemStack diamonds = new ItemStack(Material.DIAMOND, 5);

                loot.add(diamonds);
                loot.add(HorseMountItem.createHorseMount());
                loot.add(MeatHookItem.createMeatHook());
                break;
            case 2:
                ItemStack netheriteScraps = new ItemStack(Material.NETHERITE_SCRAP, 5);

                loot.add(netheriteScraps);
                loot.add(SkeletonSlayerItem.createSkeletonSlayer());
                break;
        }

        Bukkit.broadcastMessage(ChatColor.AQUA + killer.getName() + " Has slain a Boss Skeleton and received Tier " + chance + " loot!");
        if(loot.isEmpty()) return;
        Location skeletonDeath = skeleton.getLocation();
        World world = skeletonDeath.getWorld();
        loot.forEach((item) -> {
            assert world != null;
            world.dropItemNaturally(skeletonDeath, item);
        });
        skeletonArr.remove(skeleton);
    }
}
