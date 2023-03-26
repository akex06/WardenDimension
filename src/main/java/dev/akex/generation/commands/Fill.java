package dev.akex.generation.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Fill implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = ((Player) sender).getPlayer();
        Block block = player.getTargetBlockExact(10);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                block.getLocation().add(x, y, 0).getBlock().setType(Material.DIAMOND_BLOCK);
            }
        }

        return true;
    }
}
