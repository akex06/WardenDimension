package dev.akex.generation.listeners;

import dev.akex.generation.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalUse implements Listener {
    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Location location = event.getFrom();
        String portalAt = Portal.isPortalAt(location);
        if (portalAt == null) {
            return;
        }

        Player player = event.getPlayer();
        String worldName = location.getWorld().getName();
        World defaultWorld = Bukkit.getServer().getWorlds().get(0);
        event.setCancelled(true);

        if (worldName.equals(defaultWorld.getName())) {
            Portal.createTeleportPlatform(Portal.defaultTeleportLocation);
            player.teleport(Portal.defaultTeleportLocation);
        } else {
            player.teleport(defaultWorld.getSpawnLocation());
        }
    }
}
