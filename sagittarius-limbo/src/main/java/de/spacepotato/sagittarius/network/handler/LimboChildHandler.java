package de.spacepotato.sagittarius.network.handler;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.SagittariusImpl;
import de.spacepotato.sagittarius.cache.PacketCache;
import de.spacepotato.sagittarius.config.LimboConfig;
import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.mojang.BungeeCordGameProfile;
import de.spacepotato.sagittarius.mojang.GameProfile;
import de.spacepotato.sagittarius.mojang.OfflineGameProfile;
import de.spacepotato.sagittarius.mojang.SkinProperty;
import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.PacketContainer;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.handshake.ClientHandshakePacket;
import de.spacepotato.sagittarius.network.protocol.login.ClientLoginStartPacket;
import de.spacepotato.sagittarius.network.protocol.login.ServerLoginSuccessPacket;
import de.spacepotato.sagittarius.network.protocol.play.*;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusPingPacket;
import de.spacepotato.sagittarius.network.protocol.status.ClientStatusRequestPacket;
import de.spacepotato.sagittarius.util.PlayerMovementTracker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class LimboChildHandler extends ChildNetworkHandler {

	private PlayerImpl player;
	private ClientHandshakePacket handshake;
	private final Queue<Integer> keepAliveIds;
	private PlayerMovementTracker movementTracker;
	
	public LimboChildHandler(Channel channel) {
		super(channel);
		keepAliveIds = new ArrayDeque<>();
	}
	
	public void sendPacket(Packet packet) {
		channel.writeAndFlush(packet);
	}
	
	public void sendPacket(PacketContainer container) {
		channel.writeAndFlush(container);
	}
	
	public void sendPacket(ByteBuf buf) {
		channel.writeAndFlush(buf);
	}

	public int requestKeepAlive(int keepAliveId) {
		keepAliveIds.add(keepAliveId);
		return keepAliveIds.size();
	}
	
	@Override
	public void handleDisconnect() {
		if (player == null) {
            return;
        }
		synchronized (Sagittarius.getInstance().getPlayers()) {
			Sagittarius.getInstance().getPlayers().remove(player);
		}
		log.info(player.getName() + " disconnected.");
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
		LimboConfig config = Sagittarius.getInstance().getConfig();
		PacketCache cache = SagittariusImpl.getInstance().getPacketCache();
		
		GameProfile gameProfile;
		if (BungeeCordGameProfile.isBungeeCordForwarding(handshake)) {
			gameProfile = new BungeeCordGameProfile(name, handshake);
		} else {
			gameProfile = new OfflineGameProfile(name);
		}
		
		player = new PlayerImpl(this, gameProfile);
		movementTracker = new PlayerMovementTracker(player);
		
		// Accept the login attempt
		ServerLoginSuccessPacket success = new ServerLoginSuccessPacket(player.getUUID().toString(), player.getName());
		sendPacket(success);
		
		// Load PLAY-packets
		setState(State.PLAY);
		sendPacket(cache.getJoinGame());
		sendPacket(cache.getSpawnPosition());
		
		// Add to tablist for skin
		List<ServerPlayerListItemPacket.PlayerListEntry> entries = new ArrayList<>();
		GameMode gameMode = config.getTabGameMode().orElse(config.getGameMode());
		entries.add(new ServerPlayerListItemPacket.PlayerListEntry(player.getUUID(), player.getName(), player.getSkin().orElse(new SkinProperty[0]), gameMode.getId(), 0, null));
		ServerPlayerListItemPacket tablist = new ServerPlayerListItemPacket((byte) 0, entries);
		sendPacket(tablist);
		
		SagittariusImpl.getInstance().getWorldCache().send(player);
		
		sendPacket(cache.getPositionAndLook());
		sendPacket(cache.getPlayerAbilities());
		
		
		synchronized (Sagittarius.getInstance().getPlayers()) {
			Sagittarius.getInstance().getPlayers().add(player);
		}
		
		if (config.shouldSendJoinMessage()) {
			player.sendMessage(config.getJoinMessage());
		}
		log.info(player.getName() + " has logged in.");
	}
	
	// ============================================================ \\
	//                                                              \\
	//                             Play                             \\	
	//                                                              \\
	// ============================================================ \\

	@Override
	public void handleKeepAlive(ClientKeepAlivePacket packet) {
		// Keep-Alive mismatch!
		if (keepAliveIds.isEmpty() || keepAliveIds.poll() != packet.getKeepAliveId()) {
			player.kick("Invalid Keep-Alive packet received.");
        }
	}

	@Override
	public void handleClientSettings(ClientSettingsPacket packet) {
		// Properly update skin
		ServerEntityMetadataPacket metadata = new ServerEntityMetadataPacket(packet.getDisplayedSkinParts());
		sendPacket(metadata);
	}

	@Override
	public void handlePosition(ClientPositionPacket packet) {
		movementTracker.onMove(packet.getX(), packet.getY(), packet.getZ());
	}

	@Override
	public void handleLook(ClientLookPacket packet) {
		movementTracker.onRotate(packet.getYaw(), packet.getPitch());		
	}

	@Override
	public void handlePositionLook(ClientPositionLookPacket packet) {
		movementTracker.onMove(packet.getX(), packet.getY(), packet.getZ());
		movementTracker.onRotate(packet.getYaw(), packet.getPitch());
	}

}
