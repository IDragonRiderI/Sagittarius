package de.spacepotato.sagittarius.nbt;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class NBTTagList extends NBT {

	private List<NBT> value;
	private int id = 0;
	
	public NBTTagList() {
		value = new ArrayList<>();
	}
	
	public NBTTagList(int id) {
		value = new ArrayList<>();
		this.id = id;
	}
	
	@Override
	public void read(NBTInputStream in) throws Exception {
		byte type = in.readByte();
		int length = in.readInt();
		id = type;
		NBTIDs id = NBTIDs.values()[type];
		for(int i = 0; i < length; i++) {
			NBT nbt = id.newInstance();
			nbt.read(in);
			value.add(nbt);
		}
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		int i = id;
		if(i == 0 && !value.isEmpty()) {
			i = value.get(0).getId();
		}
		out.writeByte(i);
		out.writeInt(value.size());
		for(NBT nbt : value) {
			nbt.write(out);
		}
	}
	
	@Override
	public int getId() {
		return 9;
	}
	
	
}
