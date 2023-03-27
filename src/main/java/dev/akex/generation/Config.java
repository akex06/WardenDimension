package dev.akex.generation;

public class Config {
    public static String getMainWorld() {
        return Generation.config.getString("main_world");
    }
    public static int getNextPoralID() {
        int newID = Generation.config.getInt("portals.last") + 1;
        Generation.config.set("portals.last", newID);
        return newID;
    }
}
