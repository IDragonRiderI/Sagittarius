package de.spacepotato.sagittarius.network.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PacketContainer {

	private ByteBuf buffer;
	
	public PacketContainer(Packet packet) {
		try {
			buffer = packet.encode();
		} catch (Exception e) {
			log.error("Exception while encoding packet! ", e);
		}
	}
	
	public void send(Channel channel) {
		channel.writeAndFlush(buffer.retain());
	}
	
}
