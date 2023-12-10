package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagByteArray extends NBT {

	private byte[] value;

	@Override
	public void read(NBTInputStream in) throws Exception {
		int length = in.readInt();
		value = new byte[length];
		for(int i = 0; i < length; i++) {
			value[i] = in.readByte();
		}
		
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeInt(value.length);
		for(byte b : value) {
			out.writeByte(b);
		}
	}
	
	@Override
	public int getId() {
		return 7;
	}
	
}
