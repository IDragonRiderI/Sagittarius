package de.spacpotato.sagittarius.network.protocol.play;

import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerChatMessagePacket extends Packet {

	private String message;
	private byte position;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, message);
		buf.writeByte(position);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerChatMessagePacket();
	}

	@Override
	public int getId() {
		return 0x02;
	}

}
