package de.spacepotato.sagittarius.world;

import java.util.ArrayList;
import java.util.List;

import com.carrotsearch.hppc.LongObjectHashMap;
import com.carrotsearch.hppc.cursors.ObjectCursor;

import de.spacepotato.sagittarius.world.metadata.BlockMetadata;
import lombok.Getter;

@Getter
public class WorldImpl implements World {
	
	private LongObjectHashMap<ChunkImpl> chunks;
	private List<BlockMetadata> metadata;
	
	public WorldImpl() {
		chunks = new LongObjectHashMap<>();
		metadata = new ArrayList<>();
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
		for (int i = 0; i < biomes.length; i++) {
			biomes[i] = id;
		}
		
		for (ObjectCursor<ChunkImpl> chunk : chunks.values()) {
			chunk.value.setBiomes(biomes);
		}
	}

	
}
