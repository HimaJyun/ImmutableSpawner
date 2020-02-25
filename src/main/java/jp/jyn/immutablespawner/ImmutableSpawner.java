package jp.jyn.immutablespawner;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.Set;

public class ImmutableSpawner extends JavaPlugin implements Listener {
    private final Set<Material> spawnEgg = EnumSet.noneOf(Material.class);

    @Override
    public void onEnable() {
        for (Material material : Material.values()) {
            if (material.name().endsWith("_SPAWN_EGG")) {
                spawnEgg.add(material);
            }
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin) this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getClickedBlock().getType() != Material.SPAWNER) {
            return;
        }
        if (!spawnEgg.contains(e.getMaterial())) {
            return;
        }
        if (e.getPlayer().hasPermission("immutablespawner.allow")) {
            return;
        }

        e.setCancelled(true);
    }
}
