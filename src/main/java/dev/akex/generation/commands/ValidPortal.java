package dev.akex.generation.commands;

import dev.akex.generation.Portal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class ValidPortal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        Block startBlock = player.getTargetBlockExact(10);
        player.sendMessage(String.valueOf(Portal.isValid(startBlock)));

        return true;
    }
}
