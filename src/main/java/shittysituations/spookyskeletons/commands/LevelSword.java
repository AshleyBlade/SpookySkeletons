package shittysituations.spookyskeletons.commands;

import com.sun.istack.internal.NotNull;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import shittysituations.spookyskeletons.main;

import java.util.Objects;

public class LevelSword implements CommandExecutor {

    main plugin;
    public LevelSword(main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) return true; // if sender isn't player return
        Player player = (Player) commandSender; // cast sender to player
        ItemStack item = player.getInventory().getItemInMainHand(); // get player's mainhand item
        if(!Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) return true; // if item doesn't have display name return
        if(!item.getItemMeta().getDisplayName().contains("Skeleton Slayer")) return true; // if item doesn't have the name Skeleton Slayer return

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer(); // store the persistent data container
        NamespacedKey key = new NamespacedKey(plugin, "skeletonslayerkills"); // store skeleton slayer levels namespacedkey

        if(!container.has(key, PersistentDataType.INTEGER)) return true; // check if container has data
        int kills = container.get(key, PersistentDataType.INTEGER); // store the levels from persistent data
        String argument = args[0];
        int killIncrease = Integer.parseInt(argument) * 100;
        int newKills = kills + killIncrease;

        container.set(key, PersistentDataType.INTEGER, newKills); // set the level persistent data to levels + 1;

        player.sendMessage("The item should have been leveled by " + args[0] + "." + " Kill a skeleton for it to update.") ;

        return true;
    }
}
