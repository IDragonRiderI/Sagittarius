package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagInt extends NBT {

	private int value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readInt();
	}

	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeInt(value);
	}

	@Override
	public int getId() {
		return 3;
	}
	
}
