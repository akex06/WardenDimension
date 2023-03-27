package dev.akex.generation;

import dev.akex.generation.biome.DeepDarkBiomeProvider;
import dev.akex.generation.chunk.DeepDarkChunkGenerator;
import dev.akex.generation.commands.Generate;
import dev.akex.generation.listeners.IgniteEvent;
import dev.akex.generation.listeners.PortalListener;
import dev.akex.generation.listeners.StructureGeneration;
import dev.akex.generation.utils.Generator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Generation extends JavaPlugin {
    public static Generation instance;
    public static FileConfiguration config;
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        instance = this;

        saveDefaultConfig();
        config = getConfig();

        this.getCommand("generateworld").setExecutor(new Generate());

        getServer().getPluginManager().registerEvents(new IgniteEvent(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new StructureGeneration(), this);

        logger.log(Level.INFO, "Plugin enabled");

        // Will create the world when tasks are enabled (aka when you're able to create worlds)
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Generator.createWorld(Config.getMainWorld());
        }, 1L);
    }

    @Override
    public void onDisable() {
        saveConfig();
        logger.log(Level.INFO, "Plugin disabled");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        logger.log(Level.INFO, "Using deep dark generation for " + worldName);
        return new DeepDarkChunkGenerator();
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
        return new DeepDarkBiomeProvider();
    }
}
