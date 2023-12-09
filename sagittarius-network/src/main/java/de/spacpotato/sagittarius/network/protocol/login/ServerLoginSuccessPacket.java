package de.spacpotato.sagittarius.network.protocol.login;

import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerLoginSuccessPacket extends Packet {

	private String uuid;
	private String name;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, uuid);
		writeString(buf, name);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerLoginSuccessPacket();
	}

	@Override
	public int getId() {
		return 0x02;
	}

}
