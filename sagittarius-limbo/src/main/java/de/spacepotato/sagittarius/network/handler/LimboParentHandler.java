package de.spacepotato.sagittarius.network.handler;

import io.netty.channel.Channel;

public class LimboParentHandler implements ParentNetworkHandler {

	@Override
	public ChildNetworkHandler createChild(Channel channel) {
		return new LimboChildHandler(channel);
	}

}
