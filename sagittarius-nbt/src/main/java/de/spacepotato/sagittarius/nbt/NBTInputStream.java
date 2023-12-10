package de.spacepotato.sagittarius.nbt;

import java.io.DataInputStream;
import java.io.InputStream;

public class NBTInputStream extends DataInputStream {

	public NBTInputStream(InputStream in) {
		super(in);
	}

	public NBT readTag() throws Exception {
		byte type = readByte();
		if(type == 0) return null;
		readUTF();
		NBT nbt = NBTIDs.values()[type].newInstance();
		nbt.read(this);
		return nbt;
	}
	
}
