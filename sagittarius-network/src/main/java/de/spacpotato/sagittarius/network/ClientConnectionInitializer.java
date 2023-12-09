package de.spacpotato.sagittarius.network;

import java.util.concurrent.TimeUnit;

import de.spacpotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacpotato.sagittarius.network.handler.ParentNetworkHandler;
import de.spacpotato.sagittarius.network.protocol.PacketEncoder;
import de.spacpotato.sagittarius.network.protocol.PacketHandler;
import de.spacpotato.sagittarius.network.protocol.PacketLengthPrepender;
import de.spacpotato.sagittarius.network.protocol.PacketReader;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ClientConnectionInitializer extends ChannelInitializer<SocketChannel> {

	private final ParentNetworkHandler parentHandler;
	
	public ClientConnectionInitializer(ParentNetworkHandler parentHandler) {
		this.parentHandler = parentHandler;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChildNetworkHandler childHandler = parentHandler.createChild(ch);
		// TODO: Change to a proper 21-bit implementation. For now we'll support 32bit packets but this needs to change in the future!
		// Read
		ch.pipeline().addLast("frame_decoder", new ProtobufVarint32FrameDecoder());
		ch.pipeline().addLast("packet_decoder", new PacketReader(childHandler));
		ch.pipeline().addLast("packet_handler", new PacketHandler(childHandler));
		
		// Write
		ch.pipeline().addFirst("encoder", new PacketEncoder());
		ch.pipeline().addFirst("packetlength_prepender", new PacketLengthPrepender());
		
		// Timeout
		ch.pipeline().addFirst("timeout", new ReadTimeoutHandler(30, TimeUnit.SECONDS));

	}

}
