package de.spacepotato.sagittarius.mojang;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class OfflineGameProfile implements GameProfile {

	private final String name;
	private final UUID uuid;
	
	public OfflineGameProfile(String name) {
		this.name = name;
		this.uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public Optional<SkinProperty[]> getProperties() {
		return Optional.empty();
	}

}
