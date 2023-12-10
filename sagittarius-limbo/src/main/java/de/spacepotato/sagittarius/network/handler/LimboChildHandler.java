package de.spacepotato.sagittarius.network.handler;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacpotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacpotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacpotato.sagittarius.network.protocol.play.ClientKeepAlivePacket;
import de.spacpotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacpotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
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
	
}
