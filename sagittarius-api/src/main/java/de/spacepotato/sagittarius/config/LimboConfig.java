package de.spacepotato.sagittarius.config;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.world.Biome;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import de.spacepotato.sagittarius.world.Location;

import java.util.Optional;

public interface LimboConfig {

	/**
	 * Returns the hostname that the server will bind to.
	 * @return The host of the limbo server.
	 */
	String getHost();
	
	/**
	 * Returns the port on which the limbo server will bind to.
	 * @return The port of the limbo server.
	 */
	int getPort();
	
	/**
	 * Returns whether viaversion is enabled.
	 * @return true if viaversion should be used for version compatibility.
	 */
	boolean shouldUseViaVersion();
	
	/**
	 * Returns true if native networking should be preferred. At the moment this only enables epoll.
	 * @return Whether epoll should be used instead of java NIO.
	 */
	boolean shouldUseNativeNetworking();
	
	/**
	 * Returns the spawn point of the player in the current world.
	 * @return The spawnpoint of the player.
	 */
	Location getSpawnPoint();
	
	/**
	 * Returns the difficulty of the current world.
	 * @return The difficulty of the current world.
	 */
	Difficulty getDifficulty();
	
	/**
	 * Returns the world's dimension.
	 * @return The world's dimension.
	 */
	Dimension getDimension();
	
	/**
	 * Returns the player's gamemode. This value represents the actual gamemode that the player will spawn with.
	 * @return the player's gamemode.
	 */
	GameMode getGameMode();
	
	/**
	 * Returns the biome of the world.
	 * @return the world's biome.
	 */
	Biome getBiome();
	
	/**
	 * Returns an optional tablist gamemode. This is the gamemode that will be set in the tab list.
	 * You might want to change that value to hide the player's hotbar.
	 * @return An optional containing the game mode that will be set in the player's tablist.
	 */
	Optional<GameMode> getTabGameMode();
	
	/**
	 * Returns whether the player is allowed to toggle their own fly-mode.
	 * @return true if the player may toggle their flight mode.
	 */
	boolean canFly();
	
	/**
	 * Returns whether the player will join with an enabled flight mode.
	 * @return true if the player spawns with flight-mode enabled.
	 */
	boolean isAutoFly();
	
	/**
	 * Returns the fly-speed of the player.
	 * A value of 0.05 represents the default fly speed.
	 * @return The fly speed of the player.
	 */
	float getFlySpeed();
	
	/**
	 * Returns the maximum amount of keep alive packets that may be sent before disconnecting the client.
	 * Acknowledged keep-alive packets will not count towards this threshold.
	 * @return The maximum amount of unacknowledged keep-alive packets before the player times out.
	 */
	int getMaxKeepAlivePackets();
	
	/**
	 * Returns the amount of ticks between each keep-alive packet.
	 * @return the amount of ticks between keep-alive packets.
	 */
	int getKeepAliveDelay();
	
	/**
	 * Returns whether the debug information should be reduced.
	 * Coordinates will not be displayed when this option is enabled.
	 * @return true if debug information should be hidden.
	 */
	boolean isReducedDebugInfo();
	
	/**
	 * Returns whether players should be connected to the lobby after they move.
	 * @return Whether moving players should be connected to the lobby.
	 */
	boolean shouldConnectOnMove();
	
	/**
	 * Returns the distance that a player must move in one tick before getting connected.
	 * @return The distance that must be traveled in one tick before getting connected.
	 */
	double getMoveThreshold();
	
	/**
	 * Returns the square value of getMoveThreshold().
	 * @return The square value of getMoveThreshold().
	 */
	double getMoveThresholdSquared();
	
	/**
	 * Returns whether rotating players should be connected to the lobby.
	 * @return true if rotating players should be connected to the lobby.
	 */
	boolean shouldConnectOnRotate();
	
	/**
	 * This value defines how much a player must rotate in one tick before getting sent to the lobby.
	 * @return how much the player must rotate in one tick before getting sent to the lobby.
	 */
	double getRotateThreshold();
	
	/**
	 * This value defines the amount of ticks that must pass before a player can be considered "moving".
	 * If the spawn point is placed mid-air this might prevent the player from being connected while falling.
	 * @return The amount of ticks before players may be sent to the lobby.
	 */
	long getCheckDelayAfterJoin();

	/**
	 * If the attempt to send the player to the lobby fails, the Limbo will wait for this amount of ticks until it
	 * reattempts to send this player.
	 * @return The amount of ticks between connection attempts.
	 */
	long getCheckDelayBetweenAttempts();
	
	/**
	 * Returns the payload that will be sent to connect the player to the lobby.
	 * @return The payload that will be sent in the plugin message.
	 */
	byte[] getConnectPayload();
	
	/**
	 * Returns true if the player should receive a message immediately after joining.
	 * @return true if the player should receive a message after joining the server.
	 */
	boolean shouldSendJoinMessage();
	
	/**
	 * Returns the join message that will be sent to the player after joining.
	 * @return The join message.
	 */
	String getJoinMessage();
	
	/**
	 * Returns true if an actionbar should be displayed on the server.
	 * @return Whether the action bar is enabled.
	 */
	boolean shouldSendActionbar();

	/**
	 * Returns the message that will be in the action bar if it's enabled.
	 * @return The actionbar message.
	 */
	String getActionbarMessage();
	
	/**
	 * Returns the amount of ticks between actionbar packets.
	 * @return The amount of ticks between actionbar packets.
	 */
	int getActionbarIntervalTicks();

	/**
	 * Returns whether regular broadcasts should be sent.
	 * @return true if regular broadcasts should be sent.
	 */
	boolean shouldSendBroadcasts();
	
	/**
	 * Returns the broadcast message that will be sent if broadcasts are enabled.
	 * @return The broadcast message.
	 */
	String getBroadcastMessage();
	
	/**
	 * Returns the amount of ticks between broadcasts.
	 * @return The amount of ticks between broadcasts.
	 */
	int getBroadcastIntervalTicks();
	
}