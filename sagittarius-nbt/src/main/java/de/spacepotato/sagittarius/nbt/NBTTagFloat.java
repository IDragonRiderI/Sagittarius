package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagFloat extends NBT {

	private float value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readFloat();
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeFloat(value);
	}
	
	@Override
	public int getId() {
		return 5;
	}
	
}
