package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NBTTagString extends NBT {

	private String value;
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		value = in.readUTF();
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		out.writeUTF(value);
	}
	
	@Override
	public int getId() {
		return 8;
	}
	
}
