package de.spacepotato.sagittarius.network.handler;

import java.util.ArrayList;
import java.util.List;

import de.spacepotato.sagittarius.SagittariusImpl;
import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.mojang.BungeeCordGameProfile;
import de.spacepotato.sagittarius.mojang.GameProfile;
import de.spacepotato.sagittarius.mojang.OfflineGameProfile;
import de.spacepotato.sagittarius.mojang.SkinProperty;
import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacepotato.sagittarius.network.protocol.login.ServerLoginSuccessPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientKeepAlivePacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientPositionPacket;
import de.spacepotato.sagittarius.network.protocol.play.ClientSettingsPacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPlayerListItemPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
import io.netty.channel.Channel;

public class LimboChildHandler extends ChildNetworkHandler {

	private PlayerImpl player;
	private ClientHandshakePacket handshake;
	
	public LimboChildHandler(Channel channel) {
		super(channel);
	}
	
	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}

	// ============================================================ \\
	//                                                              \\
	//                           Handshake                          \\	
	//                                                              \\
	// ============================================================ \\

	
	@Override
	public void handleHandshake(ClientHandshakePacket packet) {
		// If the status is not login, then close the connection.
		// TODO: handle status
		if (packet.getNextState() != 2) {
			channel.close();
			return;
		}
		handshake = packet;
		// This will also load the new packets.
		setState(State.values()[packet.getNextState()]);
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
		String name = packet.getName();
		
		GameProfile gameProfile = null;
		if (BungeeCordGameProfile.isBungeeCordForwarding(handshake)) {
			gameProfile = new BungeeCordGameProfile(name, handshake);
		} else {
			gameProfile = new OfflineGameProfile(name);
		}
		
		player = new PlayerImpl(this, gameProfile);
		
		// Accept the login attempt
		ServerLoginSuccessPacket success = new ServerLoginSuccessPacket(player.getUUID().toString(), player.getName());
		sendPacket(success);
		
		// Load PLAY-packets
		setState(State.PLAY);
		sendPacket(SagittariusImpl.getInstance().getPacketCache().getJoinGame());
		sendPacket(SagittariusImpl.getInstance().getPacketCache().getSpawnPosition());
		
		// Add to tablist for skin
		List<ServerPlayerListItemPacket.PlayerListEntry> entries = new ArrayList<>();
		entries.add(new ServerPlayerListItemPacket.PlayerListEntry(player.getUUID(), player.getName(), player.getSkin().orElse(new SkinProperty[0]), 0, 0, null));
		ServerPlayerListItemPacket tablist = new ServerPlayerListItemPacket((byte) 0, entries);
		sendPacket(tablist);
		
		sendPacket(SagittariusImpl.getInstance().getPacketCache().getPositionAndLook());
		sendPacket(SagittariusImpl.getInstance().getPacketCache().getPlayerAbilities());

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
