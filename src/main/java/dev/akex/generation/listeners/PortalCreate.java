package dev.akex.generation.listeners;

import dev.akex.generation.Generation;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreate implements Listener {
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (!event.getWorld().getName().equals(Generation.config.getString("main_world"))) {
            return;
        }

        if (event.getBlocks().get(0).getType() == Material.REINFORCED_DEEPSLATE) {
            return;
        }

        event.setCancelled(true);
    }
}
