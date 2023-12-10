package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientPositionPacket extends Packet {

	private double x;
	private double y;
	private double z;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		onGround = buf.readBoolean();
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handlePosition(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientPositionPacket();
	}

	@Override
	public int getId() {
		return 0x04;
	}

}
