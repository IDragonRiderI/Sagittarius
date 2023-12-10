package de.spacepotato.sagittarius.nbt;

public enum NBTIDs {

	TAG_END,
	TAG_BYTE(NBTTagByte.class),
	TAG_SHORT(NBTTagShort.class),
	TAG_INT(NBTTagInt.class),
	TAG_LONG(NBTTagLong.class),
	TAG_FLOAT(NBTTagFloat.class),
	TAG_DOUBLE(NBTTagDouble.class),
	TAG_BYTE_ARRAY(NBTTagByteArray.class),
	TAG_STRING(NBTTagString.class),
	TAG_LIST(NBTTagList.class),
	TAG_COMPOUND(NBTTagCompound.class),
	TAG_INT_ARRAY(NBTTagIntArray.class),
	TAG_LONG_ARRAY(NBTTagLongArray.class);

	Class<? extends NBT> clazz; 
	
	private NBTIDs() {
	
	}
	
	private NBTIDs(Class<? extends NBT> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("deprecation")
	public NBT newInstance() throws Exception {
		return clazz.newInstance();
	}

	static int getId(Class<? extends NBT> class1) {
		for(int i = 0; i < values().length; i++) {
			if(values()[i].clazz == class1) return i;
		}
		return 0;
	}
	
}
