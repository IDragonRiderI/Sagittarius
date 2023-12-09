package de.spacepotato.sagittarius.scheduler;

import java.util.concurrent.ExecutorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SagittariusScheduledTask implements ScheduledTask {

	private final Runnable runnable;
	private final long period;
	private boolean cancel;
	private boolean sync;
	private long nextExecutionTick;
	
	public SagittariusScheduledTask(Runnable runnable, boolean sync, long period, long nextExecutionTick) {
		this.runnable = runnable;
		this.period = period;
		this.sync = sync;
		this.nextExecutionTick = nextExecutionTick;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void cancel() {
		cancel = true;
	}

	@Override
	public Runnable getTask() {
		return runnable;
	}

	@Override
	public long getPeriod() {
		return period;
	}

	@Override
	public boolean isRepeating() {
		return period != -1;
	}
	
	public boolean isSync() {
		return sync;
	}
	
	public boolean isReady(long tick) {
		return nextExecutionTick <= tick;
	}
	
	public void run() {
		try {
			runnable.run();
		} catch(Exception ex) {
			log.error("An error occurred while attempting to run scheduled task", ex);
		}
		nextExecutionTick += period;
	}
	
	public void runAsync(ExecutorService service) {
		try {
			service.submit(runnable);
		} catch(Exception ex) {
			log.error("An error occurred while attempting to run scheduled task", ex);
		}
		nextExecutionTick += period;
	}

}
