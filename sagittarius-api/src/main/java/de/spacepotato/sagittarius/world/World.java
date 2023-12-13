package de.spacepotato.sagittarius.world;

public interface World {

	/**
	 * Retrieves the chunk at the given position.
	 * If no chunk is present, a new empty chunk will be generated.
	 * 
	 * @param x The absolute x coordinate of the chunk.
	 * @param z The absolute z coordinate of the chunk.
	 * @return The chunk located at the given coordinates.
	 */
	Chunk getChunk(int x, int z);
	
}
