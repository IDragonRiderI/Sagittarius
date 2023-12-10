package de.spacepotato.sagittarius.network.protocol.status;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientStatusPingPacket extends Packet {

	private long payload;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		payload = buf.readLong();
	}
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(payload);
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleStatusPing(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientStatusPingPacket();
	}

	@Override
	public int getId() {
		return 0x01;
	}

}
