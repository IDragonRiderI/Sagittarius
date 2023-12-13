package de.spacepotato.sagittarius.world;

import lombok.Getter;

@Getter
public enum Dimension {

	OVERWORLD(0),
	NETHER(-1),
	END(1),
	;

	private final byte id;

	Dimension(int id) {
		this.id = (byte) id;
	}

}
