package de.spacepotato.sagittarius.network.protocol;

import io.netty.buffer.ByteBuf;

public interface PacketReceiver {

	void sendPacket(Packet packet);
	
	void sendPacket(PacketContainer container);
	
	void sendPacket(ByteBuf buf);
	
}
