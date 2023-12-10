package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerPlayerPositionAndLookPacket extends Packet {

	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private byte flags;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeByte(flags);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerPlayerPositionAndLookPacket();
	}

	@Override
	public int getId() {
		return 0x08;
	}

}
