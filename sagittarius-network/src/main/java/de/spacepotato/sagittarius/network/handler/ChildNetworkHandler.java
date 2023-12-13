package de.spacepotato.sagittarius.network.handler;

import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacepotato.sagittarius.network.protocol.mappings.PacketMappings;
import de.spacepotato.sagittarius.network.protocol.mappings.PacketRegistry;
import de.spacepotato.sagittarius.network.protocol.play.ClientKeepAlivePacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientSettingsPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
import io.netty.channel.Channel;
import lombok.Getter;

public abstract class ChildNetworkHandler {

	protected final Channel channel;
	@Getter
	protected State state;
	protected PacketMappings stateMappings;
	
	protected ChildNetworkHandler(Channel channel) {
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

	public abstract void handleClientSettings(ClientSettingsPacket packet);

	public abstract void handlePosition(ClientPositionPacket packet);

	public abstract void handleLook(ClientLookPacket packet);

	public abstract void handlePositionLook(ClientPositionLookPacket packet);
	
}
