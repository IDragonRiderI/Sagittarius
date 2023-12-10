package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagByte extends NBT {

	private byte value;

	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readByte();
	}

	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeByte(value);
	}
	
	@Override
	public int getId() {
		return 1;
	}
	
	
}
