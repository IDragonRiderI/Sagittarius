package de.spacpotato.sagittarius.network.protocol.play;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientKeepAlivePacket extends Packet {

	private int keepAliveId;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		keepAliveId = readVarInt(buf);
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleKeepAlive(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientKeepAlivePacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
