package de.spacepotato.sagittarius.entity;

import de.spacepotato.sagittarius.chat.ChatPosition;
import de.spacepotato.sagittarius.command.CommandSender;
import de.spacepotato.sagittarius.mojang.SkinProperty;

import java.util.Optional;
import java.util.UUID;

public interface Player extends Entity, CommandSender {

	/**
	 * Returns the player's name.
	 * @return The name of the player.
	 */
	String getName();
	
	/**
	 * Returns the player's UUID.
	 * @return The UUID of the player.
	 */
	UUID getUUID();
	
	/**
	 * Returns an optional containing the skin.
	 * The optional will be empty on servers that do not have supported forwarding mechanisms enabled.
	 * @return An optional containing the player's skin.
	 */
	Optional<SkinProperty[]> getSkin();
	
	/**
	 * Sends a message to the player.
	 * This method will not work if the player is still connecting.
	 * @param message The message to send.
	 */
	default void sendMessage(String message) {
		sendMessage(message, ChatPosition.SYSTEM);
	}
	
	/**
	 * Sends a message to the player in the desired position.
	 * This method will not work if the player is still connecting.
	 * @param message The message that will be sent to the player.
	 * @param position The position of the message.
	 */
	void sendMessage(String message, ChatPosition position);
	
	/**
	 * Disconnects the player with the given message.
	 * @param message The message that the player will see on their kick screen.
	 */
	void kick(String message);
	
	/**
	 * This method will trigger the respawn sequence for the client.
	 */
	void respawn();
	
	/**
	 * Returns whether the player is currently in the process of connecting to the server.
	 * The result will become false as soon as the player enters the PLAY state.
	 * @return true if the player is still connecting to the server.
	 */
	boolean isConnecting();
	
	/**
	 * Returns whether the connection has been fully established. 
	 * If this method returns true, the player is in the PLAY state.
	 * @return Whether the connection has been fully established and is ready to receive and send PLAY packets.
	 */
	boolean isConnected();
	
	
}
