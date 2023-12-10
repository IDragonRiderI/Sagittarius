package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerPacketJoinGame extends Packet {

	private int entityId;
	private byte gameMode;
	private byte dimension;
	private byte difficulty;
	private byte maxPlayers;
	private String levelType;
	private boolean reducedDebugInfo;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeInt(entityId);
		buf.writeByte(gameMode);
		buf.writeByte(dimension);
		buf.writeByte(difficulty);
		buf.writeByte(maxPlayers);
		writeString(buf, levelType);
		buf.writeBoolean(reducedDebugInfo);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerPacketJoinGame();
	}

	@Override
	public int getId() {
		return 0x01;
	}

}
