package de.spacepotato.sagittarius.network.protocol.login;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerLoginDisconnectPacket extends Packet {

	private String message;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, message);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerLoginDisconnectPacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
