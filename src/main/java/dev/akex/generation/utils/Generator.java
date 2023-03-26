package dev.akex.generation.utils;

import dev.akex.generation.biome.DeepDarkBiomeProvider;
import dev.akex.generation.chunk.DeepDarkChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Generator {
    public static void createWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.generator(new DeepDarkChunkGenerator());
        wc.biomeProvider(new DeepDarkBiomeProvider());
        wc.createWorld();

        File file = new File(Bukkit.getServer().getWorldContainer(), "bukkit.yml");
        FileConfiguration bukkit = YamlConfiguration.loadConfiguration(file);
        bukkit.set("worlds." + worldName + ".generator", "Generation");
        bukkit.set("worlds." + worldName + ".biome-provider", "Generation");
        try {
            bukkit.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
