package dev.akex.generation.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class DeepDarkChunkGenerator extends ChunkGenerator {
    @Override
    public void generateNoise(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData data) {
        for (int y = 51; y < 320; y++) {
            Material material = y == 51 ? Material.BEDROCK : Material.AIR;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    data.setBlock(x, y, z, material);
                }
            }
        }
    }

    @Override
    public boolean shouldGenerateNoise() {
        return true;
    }

    @Override
    public boolean shouldGenerateSurface() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return true;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return true;
    }
}
