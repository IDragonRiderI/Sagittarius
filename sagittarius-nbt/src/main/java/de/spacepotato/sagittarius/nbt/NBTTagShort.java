package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagShort extends NBT {

	private short value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readShort();
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeShort(value);
	}
	
	@Override
	public int getId() {
		return 2;
	}
	
}
