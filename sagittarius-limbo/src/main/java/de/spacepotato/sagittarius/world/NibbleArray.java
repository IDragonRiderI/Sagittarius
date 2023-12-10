package de.spacepotato.sagittarius.world;

import lombok.Data;

@Data
public class NibbleArray {

	private byte[] data;
	
	public NibbleArray() {
		data = new byte[2048];
	}
	
	public void set(int index, byte value) {
		int half = index/2;
		byte b = data[half];
		if(index%2 == 0) {
			data[half] = (byte) ((b & 0xF0) | value);
		} else {
			data[half] = (byte) ((b & 0xF0) | (value << 4));
		}
	}

}
