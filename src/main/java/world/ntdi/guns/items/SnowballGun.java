package world.ntdi.guns.items;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class SnowballGun extends Gun {
    public SnowballGun() {
        super(1, ChatColor.GREEN + "Snowball Launcher", Material.STONE_HOE, "snowballL");
    }

    @Override
    public void interactEvent(Player p, PlayerInteractEvent e) {
        Snowball snowball = p.launchProjectile(Snowball.class, p.getLocation().getDirection());
        snowball.setBounce(true);
        snowball.setGlowing(true);
        fired(snowball, p.getUniqueId());
    }

    @Override
    public void hitEvent(UUID uuid, ProjectileHitEvent e) {
        Location loc = e.getEntity().getLocation();
        World world = loc.getWorld();
        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(loc, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(1);
        Player p = Bukkit.getPlayer(uuid);
        tnt.setSource(p);
    }
}
