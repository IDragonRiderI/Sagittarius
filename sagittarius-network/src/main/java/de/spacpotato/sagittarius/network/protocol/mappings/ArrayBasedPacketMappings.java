package de.spacpotato.sagittarius.network.protocol.mappings;

import de.spacpotato.sagittarius.network.protocol.Packet;

public class ArrayBasedPacketMappings implements PacketMappings {
	
	private final Packet[] packets;
	
	
	public ArrayBasedPacketMappings(int size) {
		packets = new Packet[size];
	}
	
	@Override
	public Packet createPacket(int id) {
		if (id < 0 || id >= packets.length) return null;
		Packet packet = packets[id];
		if (packet == null) return null;
		return packet.createNewPacket();
	}

	@Override
	public void registerPacket(Packet packet) {
		packets[packet.getId()] = packet;
		System.out.println("Registered " + packet.getId());
	}

}
