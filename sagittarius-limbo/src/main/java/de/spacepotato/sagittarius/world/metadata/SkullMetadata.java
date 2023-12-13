package de.spacepotato.sagittarius.world.metadata;

import de.spacepotato.sagittarius.nbt.NBTTagCompound;
import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.play.ServerUpdateBlockEntityPacket;
import de.spacepotato.sagittarius.world.BlockPosition;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SkullMetadata extends BlockMetadata {

	private final BlockPosition position;
	private final NBTTagCompound nbt;
	
	@Override
	public Packet toPacket() {
		return new ServerUpdateBlockEntityPacket(position, (byte) 4, nbt);
	}
	
}
