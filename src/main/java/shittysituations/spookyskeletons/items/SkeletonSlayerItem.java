package shittysituations.spookyskeletons.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import shittysituations.spookyskeletons.main;

import java.util.ArrayList;

public class SkeletonSlayerItem {

    static main plugin;
    public SkeletonSlayerItem(main plugin) {
        SkeletonSlayerItem.plugin = plugin;
    }
    
    public static ItemStack createSkeletonSlayer(){
        // <---- Diamond Sword ---->
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        diamondSword.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);
        diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);

        ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
        assert diamondSwordMeta != null;
        diamondSwordMeta.setDisplayName("Skeleton Slayer");

        ArrayList<String> swordLore = new ArrayList<>();
        swordLore.add(ChatColor.AQUA + "Obtained by slaying a rare Skeleton Boss! (Tier 2)"); // index 0 of lore array
        swordLore.add(ChatColor.RED + "It enjoys the bones of skeletons!"); // index 1 of lore array
        diamondSwordMeta.setLore(swordLore);

        // Persistent Data Containers
        NamespacedKey key = new NamespacedKey(plugin, "skeletonslayerkills");
        diamondSwordMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 0);

        diamondSword.setItemMeta(diamondSwordMeta);
        return diamondSword;
    }
}
