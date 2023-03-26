package dev.akex.generation;

public class Config {
    public static String getMainWorld() {
        return Generation.config.getString("main_world");
    }
}
