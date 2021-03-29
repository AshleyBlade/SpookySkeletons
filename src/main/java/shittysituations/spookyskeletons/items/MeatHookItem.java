package shittysituations.spookyskeletons.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MeatHookItem {
    public static ItemStack createMeatHook() { // Function that creates a meat hook and returns it
        // <---- Diamond Pickaxe ---->
        ItemStack diamondPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

        diamondPickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        diamondPickaxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        diamondPickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
        diamondPickaxe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3);

        ItemMeta pickaxeMeta = diamondPickaxe.getItemMeta();
        assert pickaxeMeta != null;
        pickaxeMeta.setDisplayName("The Meat Hook");

        ArrayList<String> pickaxeLore = new ArrayList<>();
        pickaxeLore.add(ChatColor.AQUA + "Is it a weapon, or is it a tool?");
        pickaxeLore.add(ChatColor.AQUA + "Obtained by slaying a rare Skeleton Boss! (Tier 2)");
        pickaxeMeta.setLore(pickaxeLore);

        diamondPickaxe.setItemMeta(pickaxeMeta);
        return diamondPickaxe;
    }
}
