package de.spacepotato.sagittarius.network.protocol.mappings;

import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacepotato.sagittarius.network.protocol.play.*;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;

import java.util.HashMap;

public class PacketRegistry {

	private static final HashMap<State, PacketMappings> PACKETS = new HashMap<>();
	
	static {
		// We're supporting the 1.8 protocol. This means that only the 1.8 packets need to fit.
		PACKETS.put(State.HANDSHAKE, new ArrayBasedPacketMappings(1));
		PACKETS.put(State.STATUS, new ArrayBasedPacketMappings(2));
		PACKETS.put(State.LOGIN, new ArrayBasedPacketMappings(2));
		PACKETS.put(State.PLAY, new ArrayBasedPacketMappings(26));
		
		// Register the actual packets.
		// Only serverbound packets require registration.
		registerPacket(State.HANDSHAKE, new ClientHandshakePacket());
		
		registerPacket(State.STATUS, new ClientStatusRequestPacket());
		registerPacket(State.STATUS, new ClientStatusPingPacket());

		registerPacket(State.LOGIN, new ClientLoginStartPacket());
		
		registerPacket(State.PLAY, new ClientKeepAlivePacket());		
		registerPacket(State.PLAY, new ClientPositionPacket());
		registerPacket(State.PLAY, new ClientLookPacket());
		registerPacket(State.PLAY, new ClientPositionLookPacket());
		registerPacket(State.PLAY, new ClientSettingsPacket());
	}
	
	private static void registerPacket(State state, Packet packet) {
		PACKETS.get(state).registerPacket(packet);
	}
	
	public static PacketMappings getPackets(State state) {
		return PACKETS.get(state);
	}
	
}
