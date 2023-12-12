package de.spacepotato.sagittarius.viaversion.platform;


import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.handlers.ChannelHandlerContextWrapper;
import com.viaversion.viaversion.handlers.ViaCodecHandler;
import com.viaversion.viaversion.util.PipelineUtil;

import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SagittariusViaEncoder extends MessageToByteEncoder<Object> implements ViaCodecHandler {

	private final UserConnection info;
	private final MessageToByteEncoder<?> packetEncoder;

	@Override
	protected void encode(final ChannelHandlerContext ctx, Object o, final ByteBuf bytebuf) throws Exception {
		if (o instanceof Packet) {
			PipelineUtil.callEncode(this.packetEncoder, new ChannelHandlerContextWrapper(ctx, this), o, bytebuf);
		} else {
			bytebuf.writeBytes((ByteBuf) o);
		}
		
		transform(bytebuf);
	}

	@Override
	public void transform(ByteBuf bytebuf) throws Exception {
		if (!info.checkClientboundPacket())
			throw CancelEncoderException.generate(null);
		if (!info.shouldTransformPacket())
			return;
		info.transformClientbound(bytebuf, CancelEncoderException::generate);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof CancelEncoderException) return;
	}

}
