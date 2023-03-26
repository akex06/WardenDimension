package dev.akex.generation;

import dev.akex.generation.utils.Direction;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Portal {
    public Direction direction;
    public Location minLocation;
    public Location maxLocation;
    public static int maxHeight = 17;
    public static int maxWidth = 23;
    final public static Location defaultTeleportLocation = new Location(Bukkit.getServer().getWorld(Generation.config.getString("main_world")), 0, 40, 0);
    public static HashMap<Direction, Vector> directions = new HashMap<>() {{
        put(Direction.RIGHT, new Vector(1, 0, 0));
        put(Direction.LEFT, new Vector(-1, 0, 0));
        put(Direction.UP, new Vector(0, 1, 0));
        put(Direction.DOWN, new Vector(0, -1, 0));
        put(Direction.AHEAD, new Vector(0, 0, 1));
        put(Direction.BEHIND, new Vector(0, 0, -1));
    }};

    public Portal(Location minLocation, Location maxLocation, Direction direction) {
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Portal{" +
                "direction=" + direction +
                ", minLocation=" + minLocation +
                ", maxLocation=" + maxLocation +
                '}';
    }
    public static int getPortalID() {
        // Returns which ID to use next
        int newID = Generation.config.getInt("portals.last") + 1;
        Generation.config.set("portals.last", newID);
        return newID;
    }

    public void fill() {
        int portalID = getPortalID();
        Generation.config.set("portals." + portalID + ".world", minLocation.getWorld().getName());
        ArrayList<String> blocks = new ArrayList<>();

        if (direction == Direction.RIGHT) {
            for (int y = 1; y < maxLocation.getBlockY() - minLocation.getBlockY(); y++) {
                for (int x = 1; x < maxLocation.getBlockX() - minLocation.getBlockX(); x++) {
                    minLocation.clone().add(x, y, 0).getBlock().setType(Material.NETHER_PORTAL, false);
                    blocks.add((minLocation.getBlockX() + x) + ":" + (minLocation.getBlockY() + y) + ":" + minLocation.getBlockZ());
                }
            }
        } else {
            for (int y = 1; y < maxLocation.getBlockY() - minLocation.getBlockY(); y++) {
                for (int z = 1; z < maxLocation.getBlockZ() - minLocation.getBlockZ(); z++) {
                    Block block = minLocation.clone().add(0, y, z).getBlock();
                    block.setType(Material.NETHER_PORTAL, false);
                    BlockData blockData = block.getBlockData();
                    ((Orientable) blockData).setAxis(Axis.Z);
                    block.setBlockData(blockData);

                    blocks.add(minLocation.getBlockX() + ":" + (minLocation.getBlockY() + y) + ":" + (minLocation.getBlockZ() + z));
                }
            }
        }

        Generation.config.set("portals." + portalID + ".blocks", blocks);

    }

    public static String isPortalAt(Location location) {
        for (String portalID : Generation.config.getConfigurationSection("portals").getKeys(false)) {
            if (!location.getWorld().getName().equals(Generation.config.getString("portals." + portalID + ".world"))) {
                continue;
            }
            List<String> blocks = Generation.config.getStringList("portals." + portalID + ".blocks");
            if (blocks.contains(location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ())) return portalID;
        }

        return null;
    }
    public static Direction getDirection(Block startBlock) {
        if (startBlock.getLocation().add(directions.get(Direction.RIGHT)).getBlock().getType() == Material.REINFORCED_DEEPSLATE) {
            return Direction.RIGHT;
        }

        return Direction.AHEAD;
    }
    public static Block getBlockAt(Block block, Direction direction) {
        return block.getLocation().add(directions.get(direction)).getBlock();
    }
    public static Portal isValid(Block startBlock) {
        Direction startDirection = getDirection(startBlock);
        Direction direction = startDirection;
        Block block = startBlock;
        Location minLocation = null;
        Location maxLocation = null;
        for (int i = 0; i < maxHeight * 2 + maxWidth * 2; i++) {
            block = getBlockAt(block, direction);
            if (block.equals(startBlock)) break;
            if (block.getType() != Material.REINFORCED_DEEPSLATE) return null;

            if (direction == Direction.RIGHT && getBlockAt(block, Direction.UP).getType() == Material.REINFORCED_DEEPSLATE) {
                direction = Direction.UP;
            } else if (direction == Direction.UP) {
                if (getBlockAt(block, Direction.LEFT).getType() == Material.REINFORCED_DEEPSLATE) {
                    direction = Direction.LEFT;
                    maxLocation = block.getLocation();
                } else if (getBlockAt(block, Direction.BEHIND).getType() == Material.REINFORCED_DEEPSLATE) {
                    direction = Direction.BEHIND;
                    maxLocation = block.getLocation();
                }

            } else if (direction == Direction.LEFT && getBlockAt(block, Direction.DOWN).getType() == Material.REINFORCED_DEEPSLATE) {
                direction = Direction.DOWN;
            } else if (direction == Direction.DOWN) {
                if (getBlockAt(block, Direction.RIGHT).getType() == Material.REINFORCED_DEEPSLATE) {
                    direction = Direction.RIGHT;
                    minLocation = block.getLocation();
                } else if (getBlockAt(block, Direction.AHEAD).getType() == Material.REINFORCED_DEEPSLATE) {
                    direction = Direction.AHEAD;
                    minLocation = block.getLocation();
                }
            } else if (direction == Direction.AHEAD && getBlockAt(block, Direction.UP).getType() == Material.REINFORCED_DEEPSLATE) {
                direction = Direction.UP;
            } else if (direction == Direction.BEHIND && getBlockAt(block, Direction.DOWN).getType() == Material.REINFORCED_DEEPSLATE) {
                direction = Direction.DOWN;
            }
        }
        if (minLocation == null || maxLocation == null) return null;
        if (direction == Direction.RIGHT) {
            for (int y = 1; y < maxLocation.getBlockY() - minLocation.getBlockY(); y++) {
                for (int x = 1; x < maxLocation.getBlockX() - minLocation.getBlockX(); x++) {
                    if (!(minLocation.clone().add(x, y, 0).getBlock().getType() == Material.AIR)) return null;
                }
            }
        } else {
            for (int y = 1; y < maxLocation.getBlockY() - minLocation.getBlockY(); y++) {
                for (int z = 1; z < maxLocation.getBlockZ() - minLocation.getBlockZ(); z++) {
                    if (!(minLocation.clone().add(0, y, z).getBlock().getType() == Material.AIR)) return null;
                }
            }
        }

        return new Portal(minLocation, maxLocation, startDirection);
    }

    public static void createTeleportPlatform(Location location) {
        for (int y = -1; y < 3; y++) {
            for (int x = -3; x < 3; x++) {
                for (int z = -3; z < 3; z++) {
                    Block blockAt = location.clone().add(x, y, z).getBlock();
                    if (y == -1) {
                        blockAt.setType(Material.OBSIDIAN);
                        continue;
                    }
                    blockAt.setType(Material.AIR);
                }
            }
        }
    }
}
