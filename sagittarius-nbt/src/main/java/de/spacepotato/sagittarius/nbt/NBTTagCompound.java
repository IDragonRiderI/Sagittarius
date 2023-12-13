package de.spacepotato.sagittarius.nbt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class NBTTagCompound extends NBT {

	private Map<String, NBT> value;
	
	public NBTTagCompound() {
		value = new HashMap<>();
	}
	
	public void read(NBTInputStream in) throws Exception {
		byte type;
		while((type = in.readByte()) != 0) {
			String name = in.readUTF();
			NBT nbt = NBTIDs.values()[type].newInstance();
			nbt.read(in);
			value.put(name, nbt);
		}
	}
	
	@Override
	public void write(NBTOutputStream out) throws Exception {
		for(Entry<String, NBT> en : value.entrySet()) {
			int id = en.getValue().getId();
			out.writeByte(id);
			out.writeUTF(en.getKey());
			en.getValue().write(out);
		}
		out.writeByte(0);
	}
	
	@Override
	public int getId() {
		return 10;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> T getTag(String name, Class<T> clazz) {
		if(getValue().containsKey(name)) return (T) getValue().get(name);
		return null;
	}
	
}
