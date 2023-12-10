package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientPositionLookPacket extends Packet {

	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		onGround = buf.readBoolean();
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handlePositionLook(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientPositionLookPacket();
	}

	@Override
	public int getId() {
		return 0x06;
	}

}
