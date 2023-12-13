package de.spacepotato.sagittarius.mojang;

import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;

import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.util.UUIDUtil;

public class BungeeCordGameProfile implements GameProfile {

	private static final String DELIMITER = "\00";
	private static final Gson GSON = new Gson();
	
	public static boolean isBungeeCordForwarding(ClientHandshakePacket packet) {
		return packet.getServerAddress().contains(DELIMITER);
	}
	
	private final String name;
	private final UUID uuid;
	private final Optional<SkinProperty[]> properties;
	
	public BungeeCordGameProfile(String name, ClientHandshakePacket packet) {
		String[] handshakeData = packet.getServerAddress().split(DELIMITER);
		this.name = name;
		this.uuid = UUID.fromString(UUIDUtil.addDashes(handshakeData[2]));
		// Check if skin is present
		if (handshakeData.length > 3) {
			String skin = handshakeData[3];
			SkinProperty[] properties = GSON.fromJson(skin, SkinProperty[].class);
			this.properties = Optional.of(properties);
		} else {
			this.properties = Optional.empty();
		}
		
		packet.setServerAddress(handshakeData[0]);
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
		return properties;
	}
	
}
