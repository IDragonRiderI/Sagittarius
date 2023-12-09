package de.spacepotato.sagittarius.network.handler;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import io.netty.channel.Channel;

public class LimboChildHandler extends ChildNetworkHandler {

	public LimboChildHandler(Channel channel) {
		super(channel);
	}
	
}
