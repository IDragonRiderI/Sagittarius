package de.spacepotato.sagittarius;

import de.spacepotato.sagittarius.cache.PacketCache;
import de.spacepotato.sagittarius.cache.WorldCache;
import de.spacepotato.sagittarius.chat.ChatComponent;
import de.spacepotato.sagittarius.command.CommandSender;
import de.spacepotato.sagittarius.command.ConsoleCommandHandler;
import de.spacepotato.sagittarius.command.ConsoleCommandSender;
import de.spacepotato.sagittarius.config.LimboConfig;
import de.spacepotato.sagittarius.config.SagittariusConfig;
import de.spacepotato.sagittarius.entity.Player;
import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.network.SagittariusServerImpl;
import de.spacepotato.sagittarius.network.handler.LimboParentHandler;
import de.spacepotato.sagittarius.network.protocol.PacketContainer;
import de.spacepotato.sagittarius.network.protocol.play.ServerChatMessagePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerKeepAlivePacket;
import de.spacepotato.sagittarius.scheduler.SagittariusScheduler;
import de.spacepotato.sagittarius.scheduler.ScheduledTask;
import de.spacepotato.sagittarius.scheduler.Scheduler;
import de.spacepotato.sagittarius.viaversion.SagittariusViaPlatform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
public class SagittariusImpl extends Sagittarius {

	public static SagittariusImpl getInstance() {
		return (SagittariusImpl) instance;
	}
	
	private final SagittariusScheduler scheduler;
	private final SagittariusServerImpl server;
	private final SagittariusConfig config;
	@Getter
	private final PacketCache packetCache;
	private final List<Player> players;
	private final Random random;
	@Getter
	private final WorldCache worldCache;
	private final ConsoleCommandSender console;
	private final ConsoleCommandHandler commandHandler;
	
	private ScheduledTask keepAliveTask;
	private ScheduledTask actionbarTask;
	private ScheduledTask broadcastTask;
	
	public SagittariusImpl() {
		setInstance(this);
		
		// Initialize variables
		players = Collections.synchronizedList(new ArrayList<>());
		config = new SagittariusConfig();
		packetCache = new PacketCache();
		worldCache = new WorldCache();
		console = new ConsoleCommandSender();
		scheduler = new SagittariusScheduler();
		commandHandler = new ConsoleCommandHandler();
		server = new SagittariusServerImpl(new LimboParentHandler());
		random = new Random();
		
		// Start the actual server
		load();
	}

	@Override
	public SagittariusServer getServer() {
		return server;
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
	
	@Override
	public CommandSender getConsole() {
		return console;
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
		return "1.1.2";
	}
	
	// ============================================================ \\
	//                                                              \\
	//                            Logic                             \\	
	//                                                              \\
	// ============================================================ \\
	
	public void reload() {
		log.info("Reloading server...");
		config.reload();
	
		packetCache.createPackets();
		worldCache.load();
		
		server.setHostAndPort(config.getHost(), config.getPort());
		server.setNativeNetworking(config.shouldUseNativeNetworking());
		server.setNettyThreads(config.getNettyThreads());
		
		keepAliveTask.cancel();
		keepAliveTask = scheduler.repeat(this::tickKeepAlive, getConfig().getKeepAliveDelay(), getConfig().getKeepAliveDelay());
	
		if (broadcastTask != null) broadcastTask.cancel();
		if (actionbarTask != null) actionbarTask.cancel();
		
		if (getConfig().shouldSendBroadcasts()) {
			broadcastTask = scheduler.repeat(this::tickBroadcast, getConfig().getBroadcastIntervalTicks(), getConfig().getBroadcastIntervalTicks());
		}
		if (getConfig().shouldSendActionbar()) {
			actionbarTask = scheduler.repeat(this::tickActionbar, getConfig().getActionbarIntervalTicks(), getConfig().getActionbarIntervalTicks());			
		}
		
		synchronized (players) {
			for (Player player : players) {
				player.respawn();
				getWorldCache().send((PlayerImpl) player);
			}
		}
	}
	
	public void shutdown() {
		log.info("Shutdown sequence triggered!");
		
		log.info("Stopping scheduler...");
		scheduler.stopProcessing();

		commandHandler.stopCommandThread();
		
		log.info("Closing socket...");
		getServer().stop();
		SagittariusViaPlatform.destroy();
		System.exit(0);
	}
	
	private void load() {
		log.info("Starting " + getName() + " v." + getVersion() + "...");
		
		packetCache.createPackets();
		worldCache.load();
		
		server.setHostAndPort(config.getHost(), config.getPort());
		server.setNativeNetworking(config.shouldUseNativeNetworking());
		server.setNettyThreads(config.getNettyThreads());
		
		SagittariusViaPlatform.init();
		SagittariusViaPlatform.load();
		SagittariusViaPlatform.finishStartup();

		server.start();
		commandHandler.startCommandThread();
		
		keepAliveTask = scheduler.repeat(this::tickKeepAlive, getConfig().getKeepAliveDelay(), getConfig().getKeepAliveDelay());
		if (getConfig().shouldSendBroadcasts()) {
			broadcastTask = scheduler.repeat(this::tickBroadcast, getConfig().getBroadcastIntervalTicks(), getConfig().getBroadcastIntervalTicks());
		}
		if (getConfig().shouldSendActionbar()) {
			actionbarTask = scheduler.repeat(this::tickActionbar, getConfig().getActionbarIntervalTicks(), getConfig().getActionbarIntervalTicks());			
		}
		
		scheduler.startProcessing();
	}
	
	private void tickKeepAlive() {
		int threshold = getConfig().getMaxKeepAlivePackets();
		
		int keepAliveId = random.nextInt();
		List<Player> timeOut = new ArrayList<>();
		synchronized (players) {
			ServerKeepAlivePacket packet = new ServerKeepAlivePacket(keepAliveId);
			PacketContainer container = new PacketContainer(packet);
			
			for (Player player : players) {
				PlayerImpl impl = (PlayerImpl) player;
				int keepAlives = impl.requestKeepAlive(keepAliveId);
				if (keepAlives > threshold) {
					// Avoid concurrent modification exception
					timeOut.add(player);
					continue;
				}
				impl.sendPacket(container);
			}
		}
		for (Player player : timeOut) {
			player.kick("Timed out.");
		}
	}
	
	private void tickActionbar() {
		ChatComponent component = new ChatComponent(getConfig().getActionbarMessage());
		ServerChatMessagePacket packet = new ServerChatMessagePacket(component.toJson(), (byte) 2);
		PacketContainer container = new PacketContainer(packet);
		synchronized (players) {
			for (Player player : players) {
				PlayerImpl impl = (PlayerImpl) player;
				impl.sendPacket(container);
			}
		}
	}
	
	private void tickBroadcast() {
		ChatComponent component = new ChatComponent(getConfig().getBroadcastMessage());
		ServerChatMessagePacket packet = new ServerChatMessagePacket(component.toJson(), (byte) 0);
		PacketContainer container = new PacketContainer(packet);
		synchronized (players) {
			for (Player player : players) {
				PlayerImpl impl = (PlayerImpl) player;
				impl.sendPacket(container);
			}
		}
	}
	
}
