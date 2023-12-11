package de.spacepotato.sagittarius;

import de.spacepotato.sagittarius.config.LimboConfig;
import de.spacepotato.sagittarius.scheduler.Scheduler;

public abstract class Sagittarius {

	protected static Sagittarius instance;
	
	public static Sagittarius getInstance() {
		return instance;
	}
	
	public static void setInstance(Sagittarius instance) {
		if (Sagittarius.instance != null) throw new RuntimeException("Instance is already set!");
		Sagittarius.instance = instance;
	}
	
	/**
	 * Returns the instance of the underlying server.
	 * This server owns the server socket and is responsible for accepting connections.
	 * 
	 * @return The server instance responsible for accepting connections.
	 */
	public abstract SagittariusServer getServer();
	
	/**
	 * Returns an instance of the scheduler.
	 * The scheduler allows to schedule (single-run and repeating) tasks for immediate or delayed execution.
	 * 
	 * @return The scheduler instance.
	 */
	public abstract Scheduler getScheduler();
	
	/**
	 * Returns the config instance that defines how the server will behave.
	 * @return The instance to the current configuration.
	 */
	public abstract LimboConfig getConfig();
	
}
