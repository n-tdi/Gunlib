package world.ntdi.guns.items;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class TridentGun extends Gun {
    public TridentGun() {
        super(0.5, ChatColor.RED + "Trident Launcher", Material.DIAMOND_PICKAXE, "tridentL");
    }

    @Override
    public void interactEvent(Player p, PlayerInteractEvent e) {
        Trident trident = p.launchProjectile(Trident.class, p.getLocation().getDirection());
        trident.setInvulnerable(true);
        trident.setGlowing(true);
        trident.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        trident.setGravity(false);
        fired(trident, p.getUniqueId());
    }

    @Override
    public void hitEvent(UUID uuid, ProjectileHitEvent e) {
        Location loc = e.getEntity().getLocation();
        World world = loc.getWorld();
        e.getEntity().remove();
        ItemStack itemStack = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 20 * 30, 1), true);

        itemStack.setItemMeta(potionMeta);

        ThrownPotion thrownPotion = (ThrownPotion) world.spawnEntity(loc, EntityType.SPLASH_POTION);
        thrownPotion.setItem(itemStack);
    }
}
