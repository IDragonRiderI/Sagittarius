package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerRespawnPacket extends Packet {

	private int dimension;
	private byte difficulty;
	private byte gamemode;
	private String levelType;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(dimension);
		buf.writeByte(difficulty);
		buf.writeByte(gamemode);
		writeString(buf, levelType);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerRespawnPacket();
	}

	@Override
	public int getId() {
		return 0x07;
	}

}
