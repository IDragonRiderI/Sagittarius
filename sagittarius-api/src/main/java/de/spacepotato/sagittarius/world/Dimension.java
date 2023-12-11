package de.spacepotato.sagittarius.world;

public enum Dimension {

	OVERWORLD(0),
	NETHER(-1),
	END(1),
	;
	
	byte id;
	
	private Dimension(int id) {
		this.id = (byte) id;
	}
	
	public byte getId() {
		return id;
	}
	
}
