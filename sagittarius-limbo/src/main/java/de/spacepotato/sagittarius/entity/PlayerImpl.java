package de.spacepotato.sagittarius.entity;

import java.util.Optional;
import java.util.UUID;

import de.spacepotato.sagittarius.chat.ChatComponent;
import de.spacepotato.sagittarius.mojang.GameProfile;
import de.spacepotato.sagittarius.mojang.SkinProperty;
import de.spacepotato.sagittarius.network.handler.LimboChildHandler;
import de.spacepotato.sagittarius.network.protocol.State;
import de.spacepotato.sagittarius.network.protocol.play.ServerChatMessagePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerDisconnectPacket;

public class PlayerImpl implements Player {

	private final LimboChildHandler childHandler;
	private final GameProfile profile;
	
	public PlayerImpl(LimboChildHandler childHandler, GameProfile profile) {
		this.childHandler = childHandler;
		this.profile = profile;
	}
	
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
	public void sendMessage(String message) {
		ServerChatMessagePacket packet = new ServerChatMessagePacket(new ChatComponent(message).toJson(), (byte) 0);
		childHandler.sendPacket(packet);
	}

	@Override
	public void kick(String message) {
		ServerDisconnectPacket packet = new ServerDisconnectPacket(new ChatComponent(message).toJson());
		childHandler.sendPacket(packet);
	}

	@Override
	public boolean isConnecting() {
		return childHandler.getState() != State.PLAY;
	}

	@Override
	public boolean isConnected() {
		return !isConnecting();
	}

}
