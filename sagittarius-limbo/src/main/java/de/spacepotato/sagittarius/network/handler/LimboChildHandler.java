package de.spacepotato.sagittarius.network.handler;

import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientKeepAlivePacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientSettingsPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
import io.netty.channel.Channel;

public class LimboChildHandler extends ChildNetworkHandler {

	public LimboChildHandler(Channel channel) {
		super(channel);
	}

	// ============================================================ \\
	//                                                              \\
	//                           Handshake                          \\	
	//                                                              \\
	// ============================================================ \\

	
	@Override
	public void handleHandshake(ClientHandshakePacket packet) {
		
	}

	// ============================================================ \\
	//                                                              \\
	//                            Status                            \\	
	//                                                              \\
	// ============================================================ \\

	
	@Override
	public void handleStatusRequest(ClientStatusRequestPacket packet) {
		
	}

	@Override
	public void handleStatusPing(ClientStatusPingPacket packet) {
		
	}
	
	// ============================================================ \\
	//                                                              \\
	//                             Login                            \\	
	//                                                              \\
	// ============================================================ \\

	@Override
	public void handleLoginStart(ClientLoginStartPacket packet) {
		
	}
	
	// ============================================================ \\
	//                                                              \\
	//                             Play                             \\	
	//                                                              \\
	// ============================================================ \\

	@Override
	public void handleKeepAlive(ClientKeepAlivePacket packet) {
		
	}

	@Override
	public void handleClientSettings(ClientSettingsPacket packet) {
		
	}

	@Override
	public void handlePosition(ClientPositionPacket packet) {
		
	}

	@Override
	public void handleLook(ClientLookPacket packet) {
		
	}

	@Override
	public void handlePositionLook(ClientPositionLookPacket packet) {
		
	}
	
}
