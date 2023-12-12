package de.spacepotato.sagittarius.network.protocol.play;

import java.util.List;

import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.world.Chunk;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerMapChunkBulkPacket extends Packet {

	private boolean skylight;
	private List<Chunk> chunks;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeBoolean(skylight);
		writeVarInt(buf, chunks.size());
		for (Chunk chunk : chunks) {
			buf.writeInt(chunk.getX());
			buf.writeInt(chunk.getZ());
			buf.writeShort(chunk.calculatePrimaryBitMask());
		}
		for (Chunk chunk : chunks) {
			buf.writeBytes(chunk.encode((short) chunk.calculatePrimaryBitMask(), skylight, true));
		}
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerMapChunkBulkPacket();
	}

	@Override
	public int getId() {
		return 0x26;
	}

}
