package world.ntdi.guns.items;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import world.ntdi.guns.Guns;

import java.util.UUID;
import java.util.WeakHashMap;

public abstract class Gun {
    private final double cooldown;
    private final String name;
    private final Material material;
    private final String metaKey;
    private WeakHashMap<UUID, Long> cooldowns;

    public Gun(double cooldown, String name, Material material, String metaKey) {
        this.cooldown = cooldown;
        this.name = name;
        this.material = material;
        this.metaKey = metaKey;
        this.cooldowns = new WeakHashMap<>();
    }

    public final void run(final Player p, final PlayerInteractEvent e, final Gun gun) {
        UUID uuid = p.getUniqueId();
        if (!gun.cooldowns.containsKey(uuid) || (System.currentTimeMillis() - gun.cooldowns.get(uuid)) / 1000F >= gun.getCooldown()) {
            interactEvent(p, e);
            gun.cooldowns.put(uuid, System.currentTimeMillis());
        }
    }

    public final void fired(Entity e, UUID uuid) {
        e.setMetadata(metaKey, new FixedMetadataValue(Guns.getInstance(), uuid));
    }

    public abstract void interactEvent(Player p, PlayerInteractEvent e);
    public abstract void hitEvent(UUID uuid, ProjectileHitEvent e);

    public double getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public ItemStack buildItem() {
        ItemStack gun = new ItemStack(material);
        ItemMeta meta = gun.getItemMeta();
        meta.setDisplayName(name);
        gun.setItemMeta(meta);
        return gun;
    }
}
