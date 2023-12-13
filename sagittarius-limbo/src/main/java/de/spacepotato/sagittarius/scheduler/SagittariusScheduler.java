package de.spacepotato.sagittarius.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SagittariusScheduler implements Scheduler {

	private final List<SagittariusScheduledTask> tasks;
	private final ExecutorService executor;
	private long currentTick;
	private boolean running;
	
	public SagittariusScheduler() {
		tasks = Collections.synchronizedList(new ArrayList<>());
		executor = Executors.newFixedThreadPool(2);
	}
	
	@Override
	public ScheduledTask run(Runnable runnable) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, true, -1, currentTick);
		addTaskToQueue(task);
		return task;
	}

	@Override
	public ScheduledTask runLater(Runnable runnable, long delay) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, true, -1, currentTick + delay);
		addTaskToQueue(task);
		return task;
	}

	@Override
	public ScheduledTask repeat(Runnable runnable, long delay, long period) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, true, period, currentTick + delay);
		addTaskToQueue(task);
		return task;
	}

	@Override
	public ScheduledTask runAsync(Runnable runnable) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, false, -1, currentTick);
		addTaskToQueue(task);
		return task;
	}

	@Override
	public ScheduledTask runLaterAsync(Runnable runnable, long delay) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, false, -1, currentTick + delay);
		addTaskToQueue(task);
		return task;
	}

	@Override
	public ScheduledTask repeatAsync(Runnable runnable, long delay, long period) {
		SagittariusScheduledTask task = new SagittariusScheduledTask(runnable, false, period, currentTick + delay);
		addTaskToQueue(task);
		return task;
	}
	
	public void startProcessing() {
		running = true;
		List<SagittariusScheduledTask> currentTasks = new ArrayList<>();
		List<SagittariusScheduledTask> cancelledTasks = new ArrayList<>();
		while (running) {
			synchronized (tasks) {
				// Select which tasks are ready before we actually process them.
				for (SagittariusScheduledTask task : tasks) {
					if (task.isCancelled()) {
						cancelledTasks.add(task);
						continue;
					}
					if (!task.isReady(currentTick)) {
                        continue;
                    }
					currentTasks.add(task);
				}
				
				// Clean all cancelled tasks
				for (SagittariusScheduledTask task : cancelledTasks) {
					tasks.remove(task);
				}
				
				// Call all tasks that need processing.
				for (SagittariusScheduledTask task : currentTasks.stream().sorted((o1, o2) -> Boolean.compare(o2.isSync(), o1.isSync())).collect(Collectors.toList())) {
					if (task.isSync()) {
                        task.run();
                    } else {
                        task.runAsync(executor);
                    }

					if (!task.isRepeating()) {
                        tasks.remove(task);
                    }
				}
			}
			// Some clean up, so we are ready for the next tick.
			cancelledTasks.clear();
			currentTasks.clear();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				return;
			}
			currentTick++;
		}
	}
	
	public void stopProcessing() {
		running = false;
	}
	
	private void addTaskToQueue(SagittariusScheduledTask task) {
		synchronized (tasks) {
			tasks.add(task);
		}
	}

}
