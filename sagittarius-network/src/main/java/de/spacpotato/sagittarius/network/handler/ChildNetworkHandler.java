package de.spacpotato.sagittarius.network.handler;

import de.spacpotato.sagittarius.network.protocol.Packet;
import de.spacpotato.sagittarius.network.protocol.State;
import de.spacpotato.sagittarius.network.protocol.mappings.PacketMappings;
import de.spacpotato.sagittarius.network.protocol.mappings.PacketRegistry;
import io.netty.channel.Channel;

public abstract class ChildNetworkHandler {

	protected final Channel channel;
	protected State state;
	protected PacketMappings stateMappings;
	
	public ChildNetworkHandler(Channel channel) {
		this.channel = channel;
		setState(State.HANDSHAKE);
	}
	
	protected void setState(State state) {
		this.state = state;
		this.stateMappings = PacketRegistry.getPackets(state);
	}
	
	public Packet createPacket(int id) {
		return stateMappings.createPacket(id);
	}
	
	public void handleError(Throwable throwable) {
		
	}

	public void handleDisconnect() {
		
	}
	
}
