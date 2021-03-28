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

import java.util.ArrayList;
import java.util.Random;

public class SkeletonDeathEvent implements Listener {

    ArrayList<Skeleton> skeletonArr = SkeletonSpawnEvent.skeletonArr;

    @EventHandler
    public void onSkeletonDeath(EntityDeathEvent event){
        if(!event.getEntity().getType().equals(EntityType.SKELETON)) return; // skeleton died
        if(event.getEntity().getKiller() == null) return; // died to player
        Skeleton skeleton = (Skeleton) event.getEntity();
        if(!skeletonArr.contains(skeleton)) return;

        Player killer = skeleton.getKiller();
        killer.sendMessage("You have successfully killed the skeleton!");
        giveLootReward(killer, skeleton);
    }

    private void giveLootReward(Player killer, Skeleton skeleton) {
        Random random = new Random();
        int chance = random.nextInt(2) + 1;

        ArrayList<ItemStack> loot = new ArrayList<>();

        switch(chance){ // Add more loot latter
            case 1:
                ItemStack diamonds = new ItemStack(Material.DIAMOND, 5);
                ItemStack horseMount = new ItemStack(Material.SADDLE); // Add functionality to summon a horse where you are!
                ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

                // <---- Horse Mount ---->
                ItemMeta horseMeta = horseMount.getItemMeta();
                ArrayList<String> horseLore = new ArrayList<>();
                horseLore.add(ChatColor.AQUA + "<WIP> No effect right now!"); // remove when horse is added!
                horseLore.add(ChatColor.AQUA + "Summons a horse when you need it! " + ChatColor.RED + "(Right click)");
                horseLore.add(ChatColor.AQUA + "Obtained by slaying a rare skeleton Boss!");
                assert horseMeta != null;
                horseMeta.setLore(horseLore);
                horseMount.setItemMeta(horseMeta);

                // <---- Diamond Sword ---->
                diamondSword.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);
                diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);

                ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
                diamondSwordMeta.setDisplayName("Skeleton Slayer");

                ArrayList<String> swordLore = new ArrayList<>();
                swordLore.add(ChatColor.AQUA + "Obtained by slaying a rare Skeleton Boss! (Tier 1)");
                diamondSwordMeta.setLore(swordLore);

                diamondSword.setItemMeta(diamondSwordMeta);
                loot.add(diamonds);
                loot.add(horseMount);
                loot.add(diamondSword);
                break;
            case 2:
                ItemStack netherireScraps = new ItemStack(Material.NETHERITE_SCRAP, 5);
                ItemStack diamondPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

                // <---- Diamond Pickaxe ---->
                diamondPickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                diamondPickaxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
                diamondPickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
                diamondPickaxe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);

                ItemMeta pickaxeMeta = diamondPickaxe.getItemMeta();
                pickaxeMeta.setDisplayName("The Meat Hook");

                ArrayList<String> pickaxeLore = new ArrayList<>();
                pickaxeLore.add(ChatColor.AQUA + "Is it a weapon, or is it a tool?");
                pickaxeLore.add(ChatColor.AQUA + "Obtained by slaying a rare Skeleton Boss! (Tier 2)");
                pickaxeMeta.setLore(pickaxeLore);

                diamondPickaxe.setItemMeta(pickaxeMeta);
                loot.add(netherireScraps);
                loot.add(diamondPickaxe);
                break;
        }

        Bukkit.broadcastMessage(ChatColor.AQUA + killer.getName() + " Has slain a Boss Skeleton and received Tier " + chance + " loot!");
        if(loot.isEmpty()) return;
        Location skeletonDeath = skeleton.getLocation();
        World world = skeletonDeath.getWorld();
        loot.forEach((item) -> world.dropItemNaturally(skeletonDeath, item));
        skeletonArr.remove(skeleton);
    }
}
