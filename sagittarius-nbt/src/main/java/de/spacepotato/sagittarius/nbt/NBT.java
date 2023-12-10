package de.spacepotato.sagittarius.nbt;

public abstract class NBT {

	public abstract void read(NBTInputStream in) throws Exception ;
	
	public abstract void write(NBTOutputStream out) throws Exception ;
	
	public int getId() {
		return NBTIDs.getId(getClass());
	}
	
}
