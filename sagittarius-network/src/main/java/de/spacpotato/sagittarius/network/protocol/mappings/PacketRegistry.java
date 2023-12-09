package de.spacpotato.sagittarius.network.protocol.mappings;

import java.util.HashMap;

import de.spacpotato.sagittarius.network.protocol.Packet;
import de.spacpotato.sagittarius.network.protocol.State;

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
		
	}
	
	private static void registerPacket(State state, Packet packet) {
		PACKETS.get(state).registerPacket(packet);
	}
	
	public static PacketMappings getPackets(State state) {
		return PACKETS.get(state);
	}
	
}
