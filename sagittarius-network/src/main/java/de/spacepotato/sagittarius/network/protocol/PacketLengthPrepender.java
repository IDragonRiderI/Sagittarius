package de.spacepotato.sagittarius.network.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class PacketLengthPrepender extends MessageToByteEncoder<ByteBuf> {
   
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
		Packet.writeVarInt(out, in.readableBytes());
		out.writeBytes(in);
   }
}
