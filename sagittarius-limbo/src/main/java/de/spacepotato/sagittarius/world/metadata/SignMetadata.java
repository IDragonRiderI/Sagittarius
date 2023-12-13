package de.spacepotato.sagittarius.world.metadata;

import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.play.ServerUpdateSignPacket;
import de.spacepotato.sagittarius.world.BlockPosition;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignMetadata extends BlockMetadata {

	private final BlockPosition position;
	private final String line1;
	private final String line2;
	private final String line3;
	private final String line4;
	
	@Override
	public Packet toPacket() {
		return new ServerUpdateSignPacket(position, line1, line2, line3, line4);
	}
	
	
}
