package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientLookPacket extends Packet {

	private float yaw;
	private float pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		onGround = buf.readBoolean();
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleLook(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientLookPacket();
	}

	@Override
	public int getId() {
		return 0x05;
	}

}
