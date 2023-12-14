package de.spacepotato.sagittarius.entity;

import de.spacepotato.sagittarius.chat.ChatComponent;
import de.spacepotato.sagittarius.chat.ChatPosition;
import de.spacepotato.sagittarius.mojang.GameProfile;
import de.spacepotato.sagittarius.mojang.SkinProperty;
import de.spacepotato.sagittarius.network.handler.LimboChildHandler;
import de.spacepotato.sagittarius.network.protocol.Packet;
import de.spacepotato.sagittarius.network.protocol.PacketContainer;
import de.spacepotato.sagittarius.network.protocol.PacketReceiver;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.play.ServerChatMessagePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerDisconnectPacket;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerImpl implements Player, PacketReceiver {

	private final LimboChildHandler childHandler;
	private final GameProfile profile;

	@Override
	public String getName() {
		return profile.getName();
	}

	@Override
	public UUID getUUID() {
		return profile.getUUID();
	}

	@Override
	public Optional<SkinProperty[]> getSkin() {
		return profile.getProperties();
	}

	@Override
	public void sendMessage(String message, ChatPosition position) {
		ServerChatMessagePacket packet = new ServerChatMessagePacket(new ChatComponent(message).toJson(), (byte) position.ordinal());
		sendPacket(packet);
	}

	@Override
	public void kick(String message) {
		ServerDisconnectPacket packet = new ServerDisconnectPacket(new ChatComponent(message).toJson());
		sendPacket(packet);
	}

	@Override
	public boolean isConnecting() {
		return childHandler.getState() != State.PLAY;
	}

	@Override
	public boolean isConnected() {
		return !isConnecting();
	}
	
	public void sendPacket(Packet packet) {
		childHandler.sendPacket(packet);
	}
	
	public void sendPacket(PacketContainer packet) {
		childHandler.sendPacket(packet);
	}
	
	@Override
	public void sendPacket(ByteBuf buf) {
		childHandler.sendPacket(buf);
	}

	public int requestKeepAlive(int keepAliveId) {
		return childHandler.requestKeepAlive(keepAliveId);
	}

}
