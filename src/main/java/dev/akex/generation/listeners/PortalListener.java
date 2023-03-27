package dev.akex.generation.listeners;

import dev.akex.generation.Config;
import dev.akex.generation.Generation;
import dev.akex.generation.Portal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalListener implements Listener {
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (!event.getWorld().getName().equals(Config.getMainWorld())) {
            return;
        }

        if (event.getBlocks().get(0).getType() == Material.REINFORCED_DEEPSLATE) {
            return;
        }

        event.setCancelled(true);
    }

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

    @EventHandler
    public void onPortalUse(PlayerPortalEvent event) {
        Location location = event.getFrom();
        String portalAt = Portal.isPortalAt(location);
        if (portalAt == null) {
            return;
        }

        Player player = event.getPlayer();
        String worldName = location.getWorld().getName();
        World defaultWorld = Config.getLevelNameWorld();
        event.setCancelled(true);

        if (worldName.equals(defaultWorld.getName())) {
            Portal.createTeleportPlatform(Portal.defaultTeleportLocation);
            player.teleport(Portal.defaultTeleportLocation);
        } else {
            player.teleport(defaultWorld.getSpawnLocation());
        }
    }
}
