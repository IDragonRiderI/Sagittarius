package de.spacepotato.sagittarius.config;

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
	
	
	
}
