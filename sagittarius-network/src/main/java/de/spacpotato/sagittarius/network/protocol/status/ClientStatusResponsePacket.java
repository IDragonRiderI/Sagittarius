package de.spacpotato.sagittarius.network.protocol.status;

import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientStatusResponsePacket extends Packet {

	private String response;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, response);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientStatusResponsePacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
