package de.spacepotato.sagittarius.viaversion.platform;

import java.util.List;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SagittariusViaDecoder extends ByteToMessageDecoder {

	private final UserConnection info;
	private final ByteToMessageDecoder packetDecoder;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
		if (!info.checkServerboundPacket()) {
			bytebuf.readerIndex(0);
			bytebuf.writerIndex(0);
			throw CancelDecoderException.generate(null);
		}

		if (info.shouldTransformPacket()) {
			ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
			try {
				info.transformServerbound(transformedBuf, CancelDecoderException::generate);
				list.addAll(PipelineUtil.callDecode(this.packetDecoder, ctx, transformedBuf));
			} finally {
				transformedBuf.release();
			}
		} else {
			list.addAll(PipelineUtil.callDecode(this.packetDecoder, ctx, bytebuf));
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof CancelDecoderException) {
            return;
        }
		super.exceptionCaught(ctx, cause);
	}

}
