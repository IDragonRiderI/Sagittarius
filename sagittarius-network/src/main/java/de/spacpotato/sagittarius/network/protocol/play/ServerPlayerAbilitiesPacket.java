package de.spacpotato.sagittarius.network.protocol.play;

import de.spacpotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerPlayerAbilitiesPacket extends Packet {

	private byte flags;
	private float flyingSpeed;
	private float movementSpeed;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		buf.writeByte(flags);
		buf.writeFloat(flyingSpeed);
		buf.writeFloat(movementSpeed);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerPlayerAbilitiesPacket();
	}

	@Override
	public int getId() {
		return 0x39;
	}

}
