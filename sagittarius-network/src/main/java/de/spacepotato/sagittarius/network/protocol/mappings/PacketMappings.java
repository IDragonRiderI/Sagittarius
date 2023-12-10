package de.spacepotato.sagittarius.network.protocol.mappings;

import de.spacepotato.sagittarius.network.protocol.Packet;

public interface PacketMappings {
	
	void registerPacket(Packet packet);
	
	Packet createPacket(int id);

}
