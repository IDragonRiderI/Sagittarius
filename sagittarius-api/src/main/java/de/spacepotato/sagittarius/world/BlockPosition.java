package de.spacepotato.sagittarius.world;

public class BlockPosition {
	
	// The long version of a block position is encoded as follows:
	// The x coordinate takes up the first 26 bits, then 26 bits are dedicated to the z coordinate.
	// All of the other bits belong to the y coordinate.
	// Therefore we do not need to shift the y coordinate at all.
	// However, we do need to shift the z coordinate 12 bits to the left as the y coordinate occupies 12 bits.
	// The same goes for x: y occupies 12 bits and z occupies 26 bits, resulting in a 38 bit shift.
	private static final int SHIFT_Y = 26;
	private static final int SHIFT_Z = 0;
	private static final int SHIFT_X = 38;
	
	private static final int CAP_Y = 0xFFF; // 12 bits, 0000 1111 1111 1111
	private static final int CAP_Z = 0x3FFFFFF; // 26 bits, 0000 0011 1111 1111 1111 1111 1111 1111
	private static final int CAP_X = 0x3FFFFFF; // 26 bits, 0000 0011 1111 1111 1111 1111 1111 1111

	private final int x;
	private final int y;
	private final int z;
	
	public BlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public long toLong() {
		long xCapped = x & CAP_X;
		long zCapped = z & CAP_Z;
		long yCapped = y & CAP_Y;
		
		long encoded = (xCapped << SHIFT_X) | (zCapped << SHIFT_Z) | (yCapped << SHIFT_Y);
		return encoded;
	}
	
	public static BlockPosition fromLong(long encoded) {
		int x = (int) (encoded >> SHIFT_X) & CAP_X;
		int z = (int) (encoded >> SHIFT_Z) & CAP_Z;
		int y = (int) (encoded >> SHIFT_Y) & CAP_Y;
		return new BlockPosition(x, y, z);
	}
	
}
