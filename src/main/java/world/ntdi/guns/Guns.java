package world.ntdi.guns;

import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.guns.items.Gun;
import world.ntdi.guns.items.SnowballGun;
import world.ntdi.guns.items.TridentGun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Guns extends JavaPlugin implements Listener {
    private List<Gun> guns = List.of(new SnowballGun(), new TridentGun());
    private static Guns instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for (Gun gun : guns) {
            e.getPlayer().getInventory().addItem(gun.buildItem());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        for (Gun gun : guns) {
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().equals(gun.getMaterial())) {
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                    if (item.getItemMeta().getDisplayName().equals(gun.getName())) {
                        gun.run(p, e, gun);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        for (Gun gun : guns) {
            if (e.getEntity().hasMetadata(gun.getMetaKey())) {
                UUID uuid = UUID.fromString(e.getEntity().getMetadata(gun.getMetaKey()).get(0).asString());
                if (Bukkit.getOfflinePlayer(uuid).isOnline())
                    gun.hitEvent(uuid, e);
            }
        }
    }

    public static Guns getInstance() {
        return instance;
    }
}
