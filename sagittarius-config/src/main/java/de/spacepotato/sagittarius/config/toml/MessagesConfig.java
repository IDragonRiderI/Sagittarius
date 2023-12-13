package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class MessagesConfig {
	
	private boolean sendJoinMessage;
	private String joinMessage;
	
	private boolean sendActionbar;
	private String actionbarMessage;
	private int actionbarIntervalTicks;
	
	private boolean sendBroadcast;
	private String broadcastMessage;
	private int broadcastIntervalTicks;

}
