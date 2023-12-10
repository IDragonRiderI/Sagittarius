package de.spacepotato.sagittarius;

import de.spacepotato.sagittarius.network.SagittariusServerImpl;
import de.spacepotato.sagittarius.network.handler.LimboParentHandler;
import de.spacepotato.sagittarius.scheduler.SagittariusScheduler;
import de.spacepotato.sagittarius.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagittariusImpl extends Sagittarius {

	private final SagittariusScheduler scheduler;
	private final SagittariusServerImpl server;
	
	public SagittariusImpl() {
		setInstance(this);
		
		// Initialize variables
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
		server.setHostAndPort("127.0.0.1", 25564);
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
