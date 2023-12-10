package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.nbt.NBTTagCompound;
import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.world.BlockPosition;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerUpdateBlockEntityPacket extends Packet {

	private BlockPosition position;
	private byte action;
	private NBTTagCompound data;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(position.toLong());
		buf.writeByte(action);
		writeNBT(buf, data);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerUpdateBlockEntityPacket();
	}

	@Override
	public int getId() {
		return 0x35;
	}

}
