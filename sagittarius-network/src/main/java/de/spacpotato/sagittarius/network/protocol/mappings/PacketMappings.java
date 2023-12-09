package de.spacpotato.sagittarius.network.protocol.mappings;

import de.spacpotato.sagittarius.network.protocol.Packet;

public interface PacketMappings {
	
	void registerPacket(Packet packet);
	
	Packet createPacket(int id);

}
