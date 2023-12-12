package de.spacepotato.sagittarius;

import de.spacepotato.sagittarius.viaversion.MultiVersionInjector;

public interface SagittariusServer {

	/**
	 * Checks whether the server is currently accepting new connections.
	 * This method will return false if the server has not yet been started or if the channel was closed.
	 * @return true if new connections will be accepted.
	 */
	boolean isRunning();
	
	/**
	 * Starts accepting new connections on the configured host and port.
	 * This method will not do anything if the server is currently running.
	 */
	void start();
	
	/**
	 * Stops accepting new connections.
	 * This method will not do anything if the server is not running.
	 */
	void stop();
	
	/**
	 * Changes the host and the port that the server will listen on.
	 * The changes will not reflect immediately but require a restart of the server.
	 * It can be achieved by calling the stop() method followed by start().
	 * @param host The new hostname. May be 0.0.0.0 to bind on all hosts.
	 * @param port The desired port. 25565 is the default Minecraft port.
	 */
	void setHostAndPort(String host, int port);
	
	/**
	 * Sets an injector which handles multi-version support.
	 * @param injector The multi-version injector.
	 */
	void setMultiVersionInjector(MultiVersionInjector injector);
	
}
