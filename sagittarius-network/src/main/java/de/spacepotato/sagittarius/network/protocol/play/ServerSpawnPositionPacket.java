package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.world.BlockPosition;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerSpawnPositionPacket extends Packet {

	private BlockPosition position;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(position.toLong());
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerSpawnPositionPacket();
	}

	@Override
	public int getId() {
		return 0x05;
	}

}
