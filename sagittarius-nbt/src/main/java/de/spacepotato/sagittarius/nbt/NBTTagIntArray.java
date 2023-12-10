package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagIntArray extends NBT {

	private int[] value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = new int[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readInt();
		}
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeInt(value.length);
		for(int i : value) {
			out.writeInt(i);
		}
	}
	
	@Override
	public int getId() {
		return 11;
	}
	
}
