package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerEntityMetadataPacket extends Packet {

	private byte skinData;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeVarInt(buf, 0);
		// Quick hack to get it to work...
		buf.writeByte(10);
		buf.writeByte(skinData);
		buf.writeByte(127);

	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerEntityMetadataPacket();
	}

	@Override
	public int getId() {
		return 0x1C;
	}

}
