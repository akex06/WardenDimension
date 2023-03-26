package dev.akex.generation.commands;

import dev.akex.generation.Generation;
import dev.akex.generation.biome.DeepDarkBiomeProvider;
import dev.akex.generation.chunk.DeepDarkChunkGenerator;
import dev.akex.generation.utils.Generator;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Generate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return false;

        String worldName = args[0];
        if (Bukkit.getWorld(worldName) != null) {
            sender.sendMessage("World already exists");
            return false;
        }

        sender.sendMessage("Creating world '" + args[0] + "'");
        Generator.createWorld(args[0]);

        sender.sendMessage("World created");

        return true;
    }
}
