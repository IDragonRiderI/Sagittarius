package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerDisconnectPacket extends Packet {

	private String message;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, message);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerDisconnectPacket();
	}

	@Override
	public int getId() {
		return 0x40;
	}

}
