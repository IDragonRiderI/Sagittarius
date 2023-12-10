package de.spacepotato.sagittarius.network.protocol.play;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class ClientSettingsPacket extends Packet {

	private String locale;
	private byte viewDistance;
	private byte chatMode;
	private boolean chatColors;
	private byte displayedSkinParts;
	
	@Override
	public void read(ByteBuf buf) throws Exception {
		// TODO: Find better limit
		locale = readString(buf, 16);
		viewDistance = buf.readByte();
		chatMode = buf.readByte();
		chatColors = buf.readBoolean();
		displayedSkinParts = buf.readByte();
	}
	
	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleClientSettings(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientSettingsPacket();
	}

	@Override
	public int getId() {
		return 0x15;
	}

}
