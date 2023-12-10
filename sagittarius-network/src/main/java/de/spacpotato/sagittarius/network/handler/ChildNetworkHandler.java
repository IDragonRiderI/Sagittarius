package de.spacpotato.sagittarius.network.handler;

import de.spacpotato.sagittarius.network.protocol.Packet;
import de.spacpotato.sagittarius.network.protocol.State;
import de.spacpotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacpotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacpotato.sagittarius.network.protocol.mappings.PacketMappings;
import de.spacpotato.sagittarius.network.protocol.mappings.PacketRegistry;
import de.spacpotato.sagittarius.network.protocol.play.ClientKeepAlivePacket;
import de.spacpotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacpotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
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
		throwable.printStackTrace();
	}

	public void handleDisconnect() {
		
	}

	public abstract void handleHandshake(ClientHandshakePacket packet);

	public abstract void handleStatusRequest(ClientStatusRequestPacket packet);

	public abstract void handleStatusPing(ClientStatusPingPacket packet);

	public abstract void handleLoginStart(ClientLoginStartPacket packet);

	public abstract void handleKeepAlive(ClientKeepAlivePacket packet);
	
}
