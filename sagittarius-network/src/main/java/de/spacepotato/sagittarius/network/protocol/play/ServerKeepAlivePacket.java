package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerKeepAlivePacket extends Packet {

	private int keepAliveId;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeVarInt(buf, keepAliveId);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerKeepAlivePacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
