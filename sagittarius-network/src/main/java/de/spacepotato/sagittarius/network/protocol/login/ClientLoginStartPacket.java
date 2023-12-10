package de.spacepotato.sagittarius.network.protocol.login;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientLoginStartPacket extends Packet {

	private String name;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		name = readString(buf, 16);
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleLoginStart(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientLoginStartPacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
