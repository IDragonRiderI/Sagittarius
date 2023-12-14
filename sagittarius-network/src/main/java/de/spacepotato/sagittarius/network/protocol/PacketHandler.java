package de.spacepotato.sagittarius.network.protocol;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketHandler extends ChannelInboundHandlerAdapter {

	private final ChildNetworkHandler childHandler;
	
	public PacketHandler(ChildNetworkHandler childHandler) {
		this.childHandler = childHandler;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (!(msg instanceof Packet)) return;
		Packet packet = (Packet) msg;
		packet.handle(childHandler);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		childHandler.handleDisconnect();
	}
	
}
