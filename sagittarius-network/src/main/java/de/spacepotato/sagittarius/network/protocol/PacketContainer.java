package de.spacepotato.sagittarius.network.protocol;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacketContainer extends Packet {

	private int id;
	private ByteBuf buffer;
	
	public PacketContainer(Packet packet) {
		try {
			id = packet.getId();
			buffer = packet.encode();
		} catch (Exception e) {
			log.error("Exception while encoding packet! ", e);
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBytes(buffer.slice());
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public Packet createNewPacket() {
		return this;
	}
	
}
