package de.spacpotato.sagittarius.network.handler;

import io.netty.channel.Channel;

public interface ParentNetworkHandler {

	/**
	 * Creates a child handler which is responsible for handling packets.
	 * This is usually a new player connection,
	 * @param channel The netty channel associated with this connection
	 * @return A child network handler responsible for handling incoming packets.
	 */
	ChildNetworkHandler createChild(Channel channel);
	
}
