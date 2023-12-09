package de.spacepotato.sagittarius.network.handler;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacpotato.sagittarius.network.handler.ParentNetworkHandler;
import io.netty.channel.Channel;

public class LimboParentHandler implements ParentNetworkHandler {

	@Override
	public ChildNetworkHandler createChild(Channel channel) {
		return new LimboChildHandler(channel);
	}

}
