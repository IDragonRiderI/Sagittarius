package de.spacepotato.sagittarius.world;

import lombok.Data;

@Data
public class ChunkSection {

	private short[] blocks;
	private NibbleArray skyLight;
	private NibbleArray blockLight;
	
	public ChunkSection() {
		blocks = new short[4096];
	}
	
	public void initLight() {
		skyLight = new NibbleArray();
		blockLight = new NibbleArray();
	}
	
	public void set(int x, int y, int z, int typeId, byte data) {
		short val = (short) (typeId << 4 | (data & 15));
		int key = y << 8 | z << 4 | x;
		blocks[key] = val;
	}

	public short getBlock(int x, int y, int z) {
		int key = y << 8 | z << 4 | x;
		return blocks[key];		
	}
	
	
}
