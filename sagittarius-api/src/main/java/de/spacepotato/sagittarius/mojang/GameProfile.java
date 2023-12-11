package de.spacepotato.sagittarius.mojang;

import java.util.Optional;
import java.util.UUID;

public interface GameProfile {

	String getName();
	
	UUID getUUID();
	
	Optional<SkinProperty[]> getProperties();
	
	
}
