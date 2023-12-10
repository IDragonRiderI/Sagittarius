package de.spacepotato.sagittarius.network;

import de.spacepotato.sagittarius.SagittariusServer;
import de.spacepotato.sagittarius.network.handler.ParentNetworkHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagittariusServerImpl implements SagittariusServer {

	private final ParentNetworkHandler parentHandler;
	private String host;
	private int port;
	private Channel server;
	
	
	public SagittariusServerImpl(ParentNetworkHandler parentHandler) {
		this.parentHandler = parentHandler;
	}
	
	@Override
	public boolean isRunning() {
		return server != null && server.isOpen();
	}

	@Override
	public void start() {
		if (isRunning()) return;
		boolean epoll = Epoll.isAvailable();
		EventLoopGroup bossGroup = epoll ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap()
					.group(bossGroup, workerGroup)
					.channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childHandler(new ClientConnectionInitializer(parentHandler));			
			
			server = bootstrap.bind(host, port).addListener(new ChannelFutureListener() {
				
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						log.error("Unable to start server on " + host + ":" + port + "!", future.cause());
						bossGroup.shutdownGracefully();
						workerGroup.shutdownGracefully();
						return;
					}
					log.info("Server listening on " + host + ":" + port + "!");
					
					future.channel().closeFuture().addListener(new ChannelFutureListener() {
						
						public void operationComplete(ChannelFuture future) throws Exception {
							bossGroup.shutdownGracefully();
							workerGroup.shutdownGracefully();
						}
					});
				}
			}).sync().channel();
		} catch (InterruptedException e) {
			log.error("Interrupted while trying to start server!", e);
		}		
	}

	@Override
	public void stop() {
		if (!isRunning()) return;
		server.close().syncUninterruptibly();
	}

	@Override
	public void setHostAndPort(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public Channel getServer() {
		return server;
	}

}
