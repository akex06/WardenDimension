package dev.akex.generation.listeners;

import dev.akex.generation.Config;
import dev.akex.generation.Portal;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class IgniteEvent implements Listener {
    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        if (event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            return;
        }

        Block block = event.getBlock();
        if (!(block.getWorld().getName().equals("world") || block.getWorld().getName().equals(Config.getMainWorld()))) {
            return;
        }

        Portal portal = Portal.isValid(block.getLocation().add(0, -1, 0).getBlock());
        if (portal == null) {
            return;
        }

        event.setCancelled(true);
        portal.fill();
    }
}
