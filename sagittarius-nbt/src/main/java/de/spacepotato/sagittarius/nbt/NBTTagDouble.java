package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagDouble extends NBT {

	private double value;

	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readDouble();
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeDouble(value);
	}
	
	@Override
	public int getId() {
		return 6;
	}
	
}
