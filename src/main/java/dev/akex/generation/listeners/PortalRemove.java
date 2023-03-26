package dev.akex.generation.listeners;

import dev.akex.generation.Generation;
import dev.akex.generation.Portal;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PortalRemove implements Listener {
    @EventHandler
    public void onPortalBreak(BlockPhysicsEvent event) {
        if (event.getChangedType() != Material.NETHER_PORTAL && event.getBlock().getType() != Material.NETHER_PORTAL) {
            return;
        }

        String portalID = Portal.isPortalAt(event.getBlock().getLocation());
        if (portalID == null) {
            return;
        }

        Generation.config.set("portals." + portalID, null);
    }
}
