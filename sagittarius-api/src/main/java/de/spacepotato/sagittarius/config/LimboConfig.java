package de.spacepotato.sagittarius.config;

import java.util.Optional;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.world.Biome;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import de.spacepotato.sagittarius.world.Location;

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
	
}
