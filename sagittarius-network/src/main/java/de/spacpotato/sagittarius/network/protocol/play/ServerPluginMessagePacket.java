package de.spacpotato.sagittarius.network.protocol.play;

import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerPluginMessagePacket extends Packet {

	private String channel;
	private byte[] message;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeString(buf, channel);
		buf.writeBytes(message);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerPluginMessagePacket();
	}

	@Override
	public int getId() {
		return 0x3F;
	}

}
