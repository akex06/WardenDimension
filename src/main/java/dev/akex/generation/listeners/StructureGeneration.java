package dev.akex.generation.listeners;

import dev.akex.generation.Config;
import dev.akex.generation.Generation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.AsyncStructureSpawnEvent;

import java.util.List;
import java.util.logging.Level;

public class StructureGeneration implements Listener {
    @EventHandler
    public void onStructureGeneration(AsyncStructureSpawnEvent event) {
        boolean generateStructures = Config.shouldGenerateStructures();
        if (!generateStructures) return;
        if (!Config.getGeneratedWorldNames().contains(event.getWorld().getName())) return;

        String type = Config.getStructureType();
        String structureName = event.getStructure().getKey().getKey();
        List<String> structures = Config.getStructures();
        if (type.equals("whitelist")) {
            if (!structures.contains(structureName)) event.setCancelled(true);
        } else if (type.equals("blacklist")) {
            if (structures.contains(structureName)) event.setCancelled(true);
        } else {
            Generation.logger.log(Level.WARNING, "structures.type is not set to a valid value, please fix this in the config.yml");
        }
    }
}
