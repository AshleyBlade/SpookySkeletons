package shittysituations.spookyskeletons.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import static org.bukkit.Bukkit.getLogger;
import static shittysituations.spookyskeletons.events.SkeletonSpawnEvent.spawnSpecialSkeleton;

public class SpawnSkeleton implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return true;
        if(label.equalsIgnoreCase("skelespawn")){
            Player player = (Player) commandSender;
            Location playerLoc = player.getLocation();
            World world = player.getWorld();
            Skeleton skeleton = spawnSpecialSkeleton(playerLoc, world);
            if(world.getEntities().contains(skeleton)) {
                getLogger().info(player.getDisplayName() + " has spawned a Boss Skeleton");
            }
        }
        return false;
    }
}
