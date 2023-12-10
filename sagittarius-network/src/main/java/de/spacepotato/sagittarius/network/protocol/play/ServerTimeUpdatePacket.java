package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerTimeUpdatePacket extends Packet {

	private long worldAge;
	private long timeOfDay;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeLong(worldAge);
		buf.writeLong(timeOfDay);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerTimeUpdatePacket();
	}

	@Override
	public int getId() {
		return 0x03;
	}

}
