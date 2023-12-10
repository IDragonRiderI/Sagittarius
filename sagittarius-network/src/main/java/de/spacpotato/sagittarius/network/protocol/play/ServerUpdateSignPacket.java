package de.spacpotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.world.BlockPosition;
import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerUpdateSignPacket extends Packet {

	private BlockPosition position;
	private String line1;
	private String line2;
	private String line3;
	private String line4;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(position.toLong());
		writeString(buf, line1);
		writeString(buf, line2);
		writeString(buf, line3);
		writeString(buf, line4);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerUpdateSignPacket();
	}

	@Override
	public int getId() {
		return 0x33;
	}

}
