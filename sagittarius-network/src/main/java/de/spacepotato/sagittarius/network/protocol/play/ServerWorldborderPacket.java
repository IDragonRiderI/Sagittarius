package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerWorldborderPacket extends Packet {

	@AllArgsConstructor
	public static class WorldBorderConfiguration {
		
		private final double x;
		private final double z;
		private final double oldRadius;
		private final double newRadius;
		private final long speed;
		private final int portalTeleportBoundary;
		private final int warningTime;
		private final int warningBlocks;
	}
	
	private int action;
	private WorldBorderConfiguration configuration;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeVarInt(buf, action);
		
		// Set size
		if (action == 0) {
			buf.writeDouble(configuration.newRadius);
		
		// Lerp size
		} else if(action == 1) {
			buf.writeDouble(configuration.oldRadius);
			buf.writeDouble(configuration.newRadius);
			writeVarLong(buf, configuration.speed);
		
		// Set center
		} else if(action == 2) {
			buf.writeDouble(configuration.x);
			buf.writeDouble(configuration.z);
		
		// Iniitalize
		} else if(action == 3) {
			buf.writeDouble(configuration.x);
			buf.writeDouble(configuration.z);
			buf.writeDouble(configuration.oldRadius);
			buf.writeDouble(configuration.newRadius);
			writeVarLong(buf, configuration.speed);
			writeVarInt(buf, configuration.portalTeleportBoundary);
			writeVarInt(buf, configuration.warningTime);
			writeVarInt(buf, configuration.warningBlocks);
		
		// Set warning time
		} else if (action == 4) {
			writeVarInt(buf, configuration.warningTime);			
		
		// Set warning blocks
		} else if (action == 5) {
			writeVarInt(buf, configuration.warningBlocks);			
		}
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerWorldborderPacket();
	}

	@Override
	public int getId() {
		return 0x44;
	}

}
