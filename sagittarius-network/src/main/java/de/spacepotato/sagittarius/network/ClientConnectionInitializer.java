package de.spacepotato.sagittarius.network;

import java.util.concurrent.TimeUnit;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import de.spacepotato.sagittarius.network.handler.ParentNetworkHandler;
import de.spacepotato.sagittarius.network.protocol.PacketEncoder;
import de.spacepotato.sagittarius.network.protocol.PacketHandler;
import de.spacepotato.sagittarius.network.protocol.PacketLengthPrepender;
import de.spacepotato.sagittarius.network.protocol.PacketReader;
import de.spacepotato.sagittarius.viaversion.MultiVersionInjector;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ClientConnectionInitializer extends ChannelInitializer<SocketChannel> {

	private final ParentNetworkHandler parentHandler;
	private final MultiVersionInjector injector;
	
	public ClientConnectionInitializer(ParentNetworkHandler parentHandler, MultiVersionInjector injector) {
		this.parentHandler = parentHandler;
		this.injector = injector;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChildNetworkHandler childHandler = parentHandler.createChild(ch);
		
		Runnable init = () -> {
			// TODO: Change to a proper 21-bit implementation. For now we'll support 32bit packets but this needs to change in the future!
			// Read
			ch.pipeline().addLast("frame_decoder", new ProtobufVarint32FrameDecoder());
			ch.pipeline().addLast("packet_decoder", new PacketReader(childHandler));
			ch.pipeline().addLast("packet_handler", new PacketHandler(childHandler));
			
			// Write
			ch.pipeline().addFirst("packet_encoder", new PacketEncoder());
			ch.pipeline().addFirst("packetlength_prepender", new PacketLengthPrepender());
			
			// Timeout
			ch.pipeline().addFirst("timeout", new ReadTimeoutHandler(30, TimeUnit.SECONDS));
		};
		
		if (injector != null) {
			injector.initChannel(ch, init);
		} else {
			init.run();
		}
	}

}
