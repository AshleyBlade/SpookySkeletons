package shittysituations.spookyskeletons.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class HorseMountItem {
    public static ItemStack createHorseMount() {
        // <---- Horse Mount ---->
        ItemStack horseMount = new ItemStack(Material.SADDLE); // Add functionality to summon a horse where you are!

        ItemMeta horseMeta = horseMount.getItemMeta();
        ArrayList<String> horseLore = new ArrayList<>();
        horseLore.add(ChatColor.AQUA + "<WIP> No effect right now!"); // remove when horse is added!
        horseLore.add(ChatColor.AQUA + "Summons a horse when you need it! " + ChatColor.RED + "(Right click)");
        horseLore.add(ChatColor.AQUA + "Obtained by slaying a rare skeleton Boss!");
        assert horseMeta != null;
        horseMeta.setLore(horseLore);
        horseMount.setItemMeta(horseMeta);
        return horseMount;
    }
}
