package de.spacepotato.sagittarius;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.spacepotato.sagittarius.cache.PacketCache;
import de.spacepotato.sagittarius.config.LimboConfig;
import de.spacepotato.sagittarius.config.SagittariusConfig;
import de.spacepotato.sagittarius.entity.Player;
import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.network.SagittariusServerImpl;
import de.spacepotato.sagittarius.network.handler.LimboParentHandler;
import de.spacepotato.sagittarius.network.protocol.play.ServerKeepAlivePacket;
import de.spacepotato.sagittarius.scheduler.SagittariusScheduler;
import de.spacepotato.sagittarius.scheduler.ScheduledTask;
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
	private final List<Player> players;
	private final Random random;
	
	private ScheduledTask keepAliveTask;
	
	public SagittariusImpl() {
		setInstance(this);
		
		// Initialize variables
		players = Collections.synchronizedList(new ArrayList<>());
		config = new SagittariusConfig();
		packetCache = new PacketCache();
		scheduler = new SagittariusScheduler();
		server = new SagittariusServerImpl(new LimboParentHandler());
		random = new Random();
		
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
	
	@Override
	public List<Player> getPlayers() {
		return players;
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
		
		keepAliveTask = scheduler.repeat(this::tickKeepAlive, getConfig().getKeepAliveDelay(), getConfig().getKeepAliveDelay());
		
		scheduler.startProcessing();
	}
	
	@SuppressWarnings("unused")
	private void reload() {
		log.info("Reloading server...");
		server.stop();
	
		packetCache.createPackets();
		
		server.setHostAndPort(config.getHost(), config.getPort());
		server.setNativeNetworking(config.shouldUseNativeNetworking());
		
		keepAliveTask.cancel();
		keepAliveTask = scheduler.repeat(this::tickKeepAlive, getConfig().getKeepAliveDelay(), getConfig().getKeepAliveDelay());
	}
	
	private void shutdown() {
		log.info("Shutdown sequence triggered!");
		
		log.info("Stopping scheduler...");
		scheduler.stopProcessing();
		
		log.info("Closing socket...");
		getServer().stop();
	}
	
	private void tickKeepAlive() {
		int threshold = getConfig().getMaxKeepAlivePackets();
		
		int keepAliveId = random.nextInt();
		List<Player> timeOut = new ArrayList<>();
		synchronized (players) {
			ServerKeepAlivePacket packet = new ServerKeepAlivePacket(keepAliveId);
			
			for (Player player : players) {
				PlayerImpl impl = (PlayerImpl) player;
				int keepAlives = impl.requestKeepAlive(keepAliveId);
				if (keepAlives > threshold) {
					// Avoid concurrent modification exception
					timeOut.add(player);
					continue;
				}
				impl.sendPacket(packet);
			}
		}
		for (Player player : timeOut) {
			player.kick("Timed out.");
		}
	}
	
}
