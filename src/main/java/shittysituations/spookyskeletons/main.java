package shittysituations.spookyskeletons;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import shittysituations.spookyskeletons.commands.SpawnSkeleton;
import shittysituations.spookyskeletons.events.SkeletonDamageEvent;
import shittysituations.spookyskeletons.events.SkeletonDeathEvent;
import shittysituations.spookyskeletons.events.SkeletonDefenseEvent;
import shittysituations.spookyskeletons.events.SkeletonSpawnEvent;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager manager = this.getServer().getPluginManager();
        // Register commands
        this.getCommand("skelespawn").setExecutor(new SpawnSkeleton());

        // Register events
        manager.registerEvents(new SkeletonSpawnEvent(), this);
        manager.registerEvents(new SkeletonDeathEvent(), this);
        manager.registerEvents(new SkeletonDamageEvent(), this);
        manager.registerEvents(new SkeletonDefenseEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
