package de.spacepotato.sagittarius.world;

public interface Chunk {

	/**
	 * Returns the X coordinate of the chunk.
	 * @return The X coordinate of the chunk.
	 */
	int getX();
	
	/**
	 * Returns the Z coordinate of the chunk.
	 * @return The Z coordinate of the chunk.
	 */
	int getZ();

	/**
	 * Changes the block at the given chunk coordinates.
	 *
	 * @param x The relative (chunk-based) x coordinate of the block.
	 * @param y The relative (chunk-based) y coordinate of the block.
	 * @param z The relative (chunk-based) z coordinate of the block.
	 * @param typeId The new type id for the block.
	 * @param data Any additional data, if present. Otherwise, 0.
	 */
	void set(int x, int y, int z, int typeId, byte data);
	
	/**
	 * Retrieves the block at the given chunk coordinates.
	 * The result will contain both, the block id and the data.
	 * @param x The relative (chunk-based) x coordinate of the block.
	 * @param y The relative (chunk-based) y coordinate of the block.
	 * @param z The relative (chunk-based) z coordinate of the block.
	 * @return The stored information related to the block itself.
	 */
	short getBlock(int x, int y, int z);
	
	/**
	 * Calculates the bitmask for use in the chunk data packet.
	 * @return The bitmask that max be used for networking purposes.
	 */
	
	int calculatePrimaryBitMask();
	
	/**
	 * Encodes the chunk into a network friendly format.
	 * 
	 * @param bitmask A bitfield determining which cubes should be part of the byte array.
	 * @param skyLight Whether to include skylight into the encoded version.
	 * @param full Set this to true if biomes should be included.
	 * @return The byte array containing the chunks encoded in the Minecraft network format.
	 */
	byte[] encode(short bitmask, boolean skyLight, boolean full);
	
	/**
	 * Returns an estimate for the chunk's size.
	 * @return An estimate of the chunk's size in bytes.
	 */
	int getSizeEstimate(short bitmask, boolean skyLight, boolean full);
	
}
