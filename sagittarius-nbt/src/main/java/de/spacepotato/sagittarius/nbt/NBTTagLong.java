package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagLong extends NBT {

	private long value;

	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readLong();
	}

	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeLong(value);
	}
	
	@Override
	public int getId() {
		return 4;
	}
	
}

