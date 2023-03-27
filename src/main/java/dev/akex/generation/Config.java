package dev.akex.generation;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class Config {
    public static String getMainWorld() {
        return Generation.config.getString("main_world");
    }
    public static World getLevelNameWorld() {
        return Bukkit.getServer().getWorlds().get(0);
    }
    public static int getNextPoralID() {
        int newID = Generation.config.getInt("portals.last") + 1;
        Generation.config.set("portals.last", newID);
        return newID;
    }
    public static boolean shouldGenerateStructures() {
        return Generation.config.getBoolean("structures.shouldgeneratestructures");
    }
    public static String getStructureType() {
        return Generation.config.getString("structures.type");
    }
    public static List<String> getStructures() {
        return Generation.config.getStringList("structures.structures");
    }
    public static List<String> getGeneratedWorldNames() {
        return Generation.config.getStringList("worlds");
    }
}
