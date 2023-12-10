package de.spacepotato.sagittarius.nbt;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class NBTOutputStream extends DataOutputStream {

	public NBTOutputStream(OutputStream out) {
		super(out);
	}

	public void writeTag(NBT nbt) throws Exception {
		int id = nbt.getId();
		writeByte(id);
		writeUTF("");
		nbt.write(this);
	}
	
}
