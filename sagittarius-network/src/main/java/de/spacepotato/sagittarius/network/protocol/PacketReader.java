package de.spacepotato.sagittarius.network.protocol;

import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketReader extends ByteToMessageDecoder {

	private final ChildNetworkHandler childHandler;
	
	public PacketReader(ChildNetworkHandler childHandler) {
		this.childHandler = childHandler;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int packetId = Packet.readVarInt(in);
		Packet packet = childHandler.createPacket(packetId);
		if (packet == null) {
			// Pretend to read the packet.
			in.skipBytes(in.readableBytes());
			return;
		}
		packet.read(in);
		out.add(packet);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		childHandler.handleError(cause);
	}

}
