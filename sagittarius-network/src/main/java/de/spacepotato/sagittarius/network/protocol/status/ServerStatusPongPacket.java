package de.spacepotato.sagittarius.network.protocol.status;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerStatusPongPacket extends Packet {

	private long payload;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(payload);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerStatusPongPacket();
	}

	@Override
	public int getId() {
		return 0x01;
	}

}
