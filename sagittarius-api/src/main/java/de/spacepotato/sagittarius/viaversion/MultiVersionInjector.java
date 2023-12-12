package de.spacepotato.sagittarius.viaversion;

public interface MultiVersionInjector {

	void initChannel(Object channel, Runnable regularInitCallback);
	
}
