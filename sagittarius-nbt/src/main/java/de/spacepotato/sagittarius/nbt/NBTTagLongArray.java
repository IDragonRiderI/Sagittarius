package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagLongArray extends NBT {

	private long[] value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = new long[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readLong();
		}
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeInt(value.length);
		for(long l : value) out.writeLong(l);
	}
	
	@Override
	public int getId() {
		return 12;
	}
	
}
