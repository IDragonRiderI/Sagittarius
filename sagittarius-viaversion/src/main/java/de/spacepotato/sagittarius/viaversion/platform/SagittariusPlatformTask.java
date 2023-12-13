package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import de.spacepotato.sagittarius.scheduler.ScheduledTask;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

@RequiredArgsConstructor
public class SagittariusPlatformTask implements PlatformTask<ScheduledTask>{

	private final ScheduledTask task;
	
	@Override
	public @Nullable ScheduledTask getObject() {
		return task;
	}

	@Override
	public void cancel() {
		task.cancel();
	}

}
