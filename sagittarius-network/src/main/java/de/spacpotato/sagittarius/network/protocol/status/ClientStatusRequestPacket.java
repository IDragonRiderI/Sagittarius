package de.spacpotato.sagittarius.network.protocol.status;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacpotato.sagittarius.network.protocol.Packet;

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
