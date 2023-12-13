package de.spacepotato.sagittarius.scheduler;

public interface Scheduler {

	// ============================================================ \\
	//                                                              \\
	//                             Sync                             \\	
	//                                                              \\
	// ============================================================ \\
	
	/**
	 * Queues a task for immediate synchronous processing.
	 * @param runnable The task to be scheduled.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask run(Runnable runnable);
	
	/**
	 * Queues a task for delayed synchronous processing.
	 * 
	 * @param runnable The task to be scheduled.
	 * @param delay The delay in ticks (50ms) after which the task should be executed.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask runLater(Runnable runnable, long delay);
	
	/**
	 * Repeats a task synchronously until it is manually cancelled.
	 * 
	 * @param runnable The task to be scheduled.
	 * @param delay The delay in ticks (50ms) after which the task should be executed.
	 * @param period The amount of ticks between each invocation. This will have no effect until the task has run for the first time.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask repeat(Runnable runnable, long delay, long period);


	// ============================================================ \\
	//                                                              \\
	//                            Async                             \\	
	//                                                              \\
	// ============================================================ \\

	/**
	 * Queues a task for immediate asynchronous processing.
	 * @param runnable The task to be scheduled.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask runAsync(Runnable runnable);
	
	/**
	 * Queues a task for delayed asynchronous processing.
	 * 
	 * @param runnable The task to be scheduled.
	 * @param delay The delay in ticks (50ms) after which the task should be executed.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask runLaterAsync(Runnable runnable, long delay);
	
	/**
	 * Repeats a task asynchronously until it is manually cancelled.
	 * 
	 * @param runnable The task to be scheduled.
	 * @param delay The delay in ticks (50ms) after which the task should be executed.
	 * @param period The amount of ticks between each invocation. This will have no effect until the task has run for the first time.
	 * @return A ScheduledTask object for direct access to the underlying task.
	 */
	ScheduledTask repeatAsync(Runnable runnable, long delay, long period);

}
