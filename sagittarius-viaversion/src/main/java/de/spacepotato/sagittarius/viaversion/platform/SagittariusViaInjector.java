package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.viaversion.MultiVersionInjector;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public class SagittariusViaInjector implements ViaInjector, MultiVersionInjector {


	@Override
	public void inject() {
		Sagittarius.getInstance().getServer().setMultiVersionInjector(this);
	}

	@Override
	public void uninject() {
		Sagittarius.getInstance().getServer().setMultiVersionInjector(null);
	}

	@Override
	public ProtocolVersion getServerProtocolVersion() {
		return ProtocolVersion.v1_8;
	}

	@Override
	public JsonObject getDump() {
		// TODO: properly implement dumps
		return new JsonObject();
	}
	
	@Override
	public void initChannel(Object channelObject, Runnable regularInitCallback) {
		Channel channel = (Channel) channelObject;
		if (!Via.getAPI().getServerVersion().isKnown() || !(channel instanceof SocketChannel)) {
			regularInitCallback.run();
			return;
		}
		UserConnection info = new UserConnectionImpl(channel);
		new ProtocolPipelineImpl(info);
		regularInitCallback.run();

		@SuppressWarnings("rawtypes")
		MessageToByteEncoder encoder = new SagittariusViaEncoder(info, (MessageToByteEncoder) channel.pipeline().get(getEncoderName()));
		ByteToMessageDecoder decoder = new SagittariusViaDecoder(info, (ByteToMessageDecoder) channel.pipeline().get(getDecoderName()));
		
		channel.pipeline().replace(getEncoderName(), getEncoderName(), encoder);
		channel.pipeline().replace(getDecoderName(), getDecoderName(), decoder);
	}
	
	@Override
	public String getEncoderName() {
		return "packet_encoder";
	}
	
	@Override
	public String getDecoderName() {
		return "packet_decoder";
	}
	
}
