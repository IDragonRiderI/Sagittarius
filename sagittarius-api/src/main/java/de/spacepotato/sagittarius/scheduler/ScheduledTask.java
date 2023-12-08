package de.spacepotato.sagittarius.scheduler;

public interface ScheduledTask {

	/**
	 * Returns whether the task has been manually cancelled.
	 * This method will not return true until the cancel method has been invoked.
	 * @return Whether this task has been cancelled.
	 */
	boolean isCancelled();
	
	/**
	 * Cancels this task.
	 * Calling this method will not interrupt the task itself if it is currently running.
	 * It will instead cause the task to not be considered by the scheduler again.
	 * This method will have no effect if the task is non-repeating and has already been executed.
	 */
	void cancel();
	
	/**
	 * Returns the actual task that will be executed.
	 * @return The task that will be executed.
	 */
	Runnable getTask();
	
	/**
	 * Tasks may repeat. If this task does repeat, this method will return the number of ticks between each invocation.
	 * Non-repeating tasks will return -1.
	 * @return Returns the amount of ticks between invocations or -1 if this task is non-repeating.
	 */
	long getPeriod();
	
	/**
	 * Returns if this task is a repeating task.
	 * @return true if this task is a repeating task.
	 */
	boolean isRepeating();
	
}
