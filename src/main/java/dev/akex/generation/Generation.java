package dev.akex.generation;

import dev.akex.generation.biome.DeepDarkBiomeProvider;
import dev.akex.generation.chunk.DeepDarkChunkGenerator;
import dev.akex.generation.commands.Fill;
import dev.akex.generation.commands.Generate;
import dev.akex.generation.commands.ValidPortal;
import dev.akex.generation.listeners.IgniteEvent;
import dev.akex.generation.listeners.PortalCreate;
import dev.akex.generation.listeners.PortalRemove;
import dev.akex.generation.listeners.PortalUse;
import dev.akex.generation.utils.Generator;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public final class Generation extends JavaPlugin {
    public static Generation instance;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabling plugin");

        instance = this;
        config = getConfig();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.getCommand("generate").setExecutor(new Generate());
        this.getCommand("isvalid").setExecutor(new ValidPortal());
        this.getCommand("fill").setExecutor(new Fill());

        getServer().getPluginManager().registerEvents(new IgniteEvent(), this);
        getServer().getPluginManager().registerEvents(new PortalUse(), this);
        getServer().getPluginManager().registerEvents(new PortalRemove(), this);
        getServer().getPluginManager().registerEvents(new PortalCreate(), this);

        getLogger().log(Level.INFO, "Plugin enabled");

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Generator.createWorld(getConfig().getString("main_world"));
        }, 1L);
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().log(Level.INFO, "Plugin disabled");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        getLogger().log(Level.INFO, "Using deep dark generation for " + worldName);
        return new DeepDarkChunkGenerator();
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
        return new DeepDarkBiomeProvider();
    }
}
