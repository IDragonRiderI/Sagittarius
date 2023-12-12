package de.spacepotato.sagittarius.world;

import lombok.Data;

@Data
public class ChunkImpl implements Chunk {

	private int x;
	private int z;
	
	private ChunkSection[] chunks;
	private byte[] biomes;
	
	public ChunkImpl(int x, int z) {
		this.x = x;
		this.z = z;
		chunks = new ChunkSection[16];
		biomes = new byte[256];
	}
	
	public int calculatePrimaryBitMask() {
		int bitmask = 0;
		// TODO: Proper calculation required.
		// This check always yields the same results as chunks.length will always be 16.
		for(int i = 0; i < chunks.length; i++) {
			bitmask |= (1 << i);
		}
		return bitmask;
	}

	public void set(int x, int y, int z, int typeId, byte data) {
		if(y < 0 || y >= 256) return;
		int sectionNumber = y >> 4;
		if(chunks[sectionNumber] == null) {
			chunks[sectionNumber] = new ChunkSection();
		}
		chunks[sectionNumber].set(x, y & 15, z, typeId, data);
	}
	
	public short getBlock(int x, int y, int z) {
		if(y < 0 || y >= 256) return 0;
		int sectionNumber = y >> 4;
		if(chunks[sectionNumber] == null) {
			chunks[sectionNumber] = new ChunkSection();
		}
		return chunks[sectionNumber].getBlock(x, y & 15, z);
	}
	
	public byte[] encode(short bitmask, boolean skyLight, boolean full) {
		ChunkSection[] sections = new ChunkSection[full ? 16 : Integer.bitCount(bitmask)];
		// Include all the sections we want to send
		int sectionNumber = 0;
		for(int i = 0; i < chunks.length; i++) {
			short mask = (short) (1 << i);
			// Chunk section is present in our bitmask?
			if((bitmask & mask) == mask) {
				sections[sectionNumber++] = chunks[i];
			}
		}
		
		// 16^3 = 1 chunk cube. We have multiple chunk cubes.
		int blocks = 16*16*16*sections.length;
		// 1 short per block + a nibble (= half a byte) of blocklight + potentially half a nibble of skylight + 256 biomes
		byte[] bytes = new byte[blocks * 2 + (skyLight ? blocks : blocks/2) + (full ? 256 : 0)];
		
		int pos = 0;
		for(ChunkSection s : sections) {
			// Chunk is present
			if(s != null) {
				for(short sh : s.getBlocks()) {
					bytes[pos++] = (byte) (sh & 0xFF);
					bytes[pos++] = (byte) (sh >> 8);
				}
			// Chunk is air
			} else {
				for(int i = 0; i < 4096; i++) {
					bytes[pos++] = 0;
					bytes[pos++] = 0;
				}
			}
		}
		
		// We do not have actual light calculation. Just pretend everything is lit.
		byte fullBright = (byte) 0xFF;
		
		for(ChunkSection s : sections) {
			if(s != null && s.getBlockLight() != null) {
				System.arraycopy(s.getBlockLight().getData(), 0, bytes, pos, s.getBlockLight().getData().length);
				pos += s.getBlockLight().getData().length;
			} else {
				for(int i = 0; i < 2048; i++) {
					bytes[pos++] = fullBright;
				}
			}
		}
		
		if(skyLight) {
			for(ChunkSection s : sections) {
				if(s != null && s.getSkyLight() != null) {
					System.arraycopy(s.getSkyLight().getData(), 0, bytes, pos, s.getSkyLight().getData().length);
					pos += s.getSkyLight().getData().length;
				} else {
					for(int i = 0; i < 2048; i++) {
						bytes[pos++] = fullBright;
					}
				}
			}
		}
		
		if(full) {
			for(byte b : biomes) {
				bytes[pos++] = b;
			}
		}
		return bytes;
	}

	@Override
	public int getSizeEstimate(short bitmask, boolean skyLight, boolean full) {
		int sections = full ? 16 : Integer.bitCount(bitmask);
		int blocks = 16*16*16*sections;

		return blocks * 2 + (skyLight ? blocks : blocks/2) + (full ? 256 : 0);
	}

	
}
