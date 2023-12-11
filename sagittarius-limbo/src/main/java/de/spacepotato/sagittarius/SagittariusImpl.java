package de.spacepotato.sagittarius;

import de.spacepotato.sagittarius.cache.PacketCache;
import de.spacepotato.sagittarius.config.LimboConfig;
import de.spacepotato.sagittarius.config.SagittariusConfig;
import de.spacepotato.sagittarius.network.SagittariusServerImpl;
import de.spacepotato.sagittarius.network.handler.LimboParentHandler;
import de.spacepotato.sagittarius.scheduler.SagittariusScheduler;
import de.spacepotato.sagittarius.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagittariusImpl extends Sagittarius {

	public static SagittariusImpl getInstance() {
		return (SagittariusImpl) instance;
	}
	
	private final SagittariusScheduler scheduler;
	private final SagittariusServerImpl server;
	private final SagittariusConfig config;
	private final PacketCache packetCache;
	
	public SagittariusImpl() {
		setInstance(this);
		
		// Initialize variables
		config = new SagittariusConfig();
		packetCache = new PacketCache();
		scheduler = new SagittariusScheduler();
		server = new SagittariusServerImpl(new LimboParentHandler());
		
		// Start the actual server
		load();
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	@Override
	public SagittariusServer getServer() {
		return null;
	}

	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	@Override
	public LimboConfig getConfig() {
		return config;
	}
	
	public PacketCache getPacketCache() {
		return packetCache;
	}
	
	// ============================================================ \\
	//                                                              \\
	//                           Generic                            \\	
	//                                                              \\
	// ============================================================ \\
	
	public String getName() {
		return "Sagittarius";
	}
	
	public String getVersion() {
		return "0.0.1";
	}
	
	// ============================================================ \\
	//                                                              \\
	//                            Logic                             \\	
	//                                                              \\
	// ============================================================ \\
	
	private void load() {
		log.info("Starting " + getName() + " v." + getVersion() + "...");
		
		packetCache.createPackets();
		
		server.setHostAndPort(config.getHost(), config.getPort());
		server.setNativeNetworking(config.shouldUseNativeNetworking());
		server.start();
		
		scheduler.startProcessing();
	}
	
	private void shutdown() {
		log.info("Shutdown sequence triggered!");
		
		log.info("Stopping scheduler...");
		scheduler.stopProcessing();
		
		log.info("Closing socket...");
		getServer().stop();
		
		
	}
	
}
