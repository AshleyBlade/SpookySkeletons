package shittysituations.spookyskeletons.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

public class SkeletonDamageEvent implements Listener {

    ArrayList<Skeleton> skeletonArr = SkeletonSpawnEvent.skeletonArr;

    @EventHandler
    public void onSkeletonDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Arrow)) return; // if damager isn't arrow return

        Arrow arrow = (Arrow) event.getDamager(); // store arrow for future
        if(!(arrow.getShooter() instanceof Skeleton)) return; // if arrow's shooter isn't a skeleton return
        if(!(event.getEntity() instanceof Player)) return; // if damaged entity is not player return

        Skeleton skeleton = (Skeleton) arrow.getShooter(); // store skeleton
        if(!skeletonArr.contains(skeleton)) return; // if skeleton arr doesn't contain this skeleton return;

        Player player = (Player) event.getEntity(); // store player
        Location playerLoc = player.getLocation(); // store player location

        double damage = event.getDamage(); // store the damage dealt
        double maxHealth = 20; // store the skeletons max health

        World world = player.getWorld(); // store the world this happened in
        int duration = 20; // store the duration of tick based events

        Random random = new Random(); // create a new instance of random
        int chance = random.nextInt(10) + 1; // create a number between 1 and 10

        // ADD EFFECTS TO PLAYER/WORLD -> tested and work
        switch(chance) {
            case 1: // if chance is equal to 1 skeleton turns invisible for 5 seconds
                getLogger().info("The skeleton should be invisible");
                PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, duration * 10, 1, false, false);
                skeleton.addPotionEffect(invis);
                break;
            case 2: // if chance is equal to 2 skeleton steals life
                double skeleHealth = skeleton.getHealth();
                if (skeleHealth < maxHealth) // if current health is lower than max health
                    if(skeleHealth == maxHealth){
                        getLogger().info("The skeleton attempted to heal!");
                        return;
                    } else if(skeleHealth + damage > maxHealth) { // if the skeleton current health + damage > max health.
                        getLogger().info("The skeleton is at max health! " + skeleHealth);
                        skeleton.setHealth(maxHealth); // heal to max health
                        return;
                    }
                getLogger().info("The skeleton has been healed by " + damage + "!");
                skeleton.setHealth(skeleHealth + damage);
                break;
            case 3: // Instant Damage 2
                getLogger().info("The target should have taken 12 damage!");
                event.setDamage(12);
                break;
            case 4: // Explosive shot
                getLogger().info("The skeleton shot an explosive arrow");
                world.createExplosion(playerLoc, 2, true, false);
                break;
            case 5: // Poison arrows
                getLogger().info("The skeleton shot a poison arrow!");
                PotionEffect poison = new PotionEffect(PotionEffectType.POISON, duration * 5, 2, true, true);
                player.addPotionEffect(poison);
                break;
            case 6: // charged creeper
                getLogger().info("The skeleton shot a creeper!");
                event.setDamage(0);
                world.spawnEntity(playerLoc, EntityType.CREEPER);
                world.strikeLightning(playerLoc);
                break;
            case 7: // set on fire
                getLogger().info("The skeleton set the player on fire!");
                player.setFireTicks(duration * 10);
                break;
            case 8: // slowness
                getLogger().info("The skeleton slowed the player!");
                PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, duration * 20, 3, true, true);
                player.addPotionEffect(slowness);
                break;
            case 9: // blindness
                getLogger().info("The skeleton made the player blind!");
                PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, duration * 20, 3, true, true);
                player.addPotionEffect(blindness);
                break;
            case 10: // levitation
                getLogger().info("The skeleton made the player levitate!");
                PotionEffect levitation = new PotionEffect(PotionEffectType.LEVITATION, duration * 5, 3, true, true);
                player.addPotionEffect(levitation);
                break;
            default:
                System.out.println("Something has gone wrong getting the effect onSkeletonDamage:48");
                break;
        }
    }
}
