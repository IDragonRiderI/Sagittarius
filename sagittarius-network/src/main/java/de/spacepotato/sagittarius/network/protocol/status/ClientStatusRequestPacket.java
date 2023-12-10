package de.spacepotato.sagittarius.network.protocol.status;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;

public class ClientStatusRequestPacket extends Packet {

	@Override
	public void handle(ChildNetworkHandler childHandler) {
		childHandler.handleStatusRequest(this);
	}
	
	@Override
	public Packet createNewPacket() {
		return new ClientStatusRequestPacket();
	}

	@Override
	public int getId() {
		return 0x00;
	}

}
