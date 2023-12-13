package de.spacepotato.sagittarius.world;

import com.carrotsearch.hppc.LongObjectHashMap;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import de.spacepotato.sagittarius.world.metadata.BlockMetadata;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class WorldImpl implements World {
	
	private final LongObjectHashMap<ChunkImpl> chunks;
	private final List<BlockMetadata> metadata;
	
	public WorldImpl() {
		chunks = new LongObjectHashMap<>();
		metadata = new ArrayList<>();
	}
	
	public void expand() {
		// Fix some weird chunk glitches by slightly expanding the world.
		int minX = 0;
		int maxX = 0;
		int minZ = 0;
		int maxZ = 0;
		for (ObjectCursor<ChunkImpl> chunkCursor : chunks.values()) {
			Chunk chunk = chunkCursor.value;
			minX = Math.min(minX, chunk.getX());
			maxX = Math.max(maxX, chunk.getX());
			minZ = Math.min(minZ, chunk.getZ());
			maxZ = Math.max(maxZ, chunk.getZ());
		}
		
		for (int x = minX - 2; x <= maxX + 2; x++) {
			for (int z = minZ - 2; z <= maxZ + 2; z++) {
				long id = getId(x, z);
				if (!chunks.containsKey(id)) {
					chunks.put(id, new ChunkImpl(x, z));
				}				
			}
		}
	}

	public ChunkImpl getChunk(int x, int z) {
		int chunkX = (int)Math.floor(x / 16D);
		int chunkZ = (int)Math.floor(z / 16D);
		
		long id = getId(chunkX, chunkZ);
		ChunkImpl c = chunks.get(id);
		if(c == null) {
			chunks.put(id, c = new ChunkImpl(chunkX, chunkZ));
		}
		return c;
	}

	public ChunkImpl getChunkByChunkPosition(int x, int z) {
		long id = getId(x, z);
		ChunkImpl c = chunks.get(id);
		if(c == null) {
			chunks.put(id, c = new ChunkImpl(x, z));
		}
		return c;
	}
	
	private long getId(int x, int z) {
        return ((long) x << 32) + z - Integer.MIN_VALUE;
	}

	public void setTypeIdAndData(int x, int y, int z, short type, byte data) {
		ChunkImpl c = getChunk(x, z);
		int chunkX = x & 15;
		int chunkZ = z & 15;
		
		c.set(chunkX, y, chunkZ, type, data);
	}

	public void applyBiome(Biome biome) {
		byte id = (byte) biome.ordinal();
		byte[] biomes = new byte[256];
        Arrays.fill(biomes, id);
		
		for (ObjectCursor<ChunkImpl> chunk : chunks.values()) {
			chunk.value.setBiomes(biomes);
		}
	}

	
}
