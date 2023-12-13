package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class ConnectConfig {
	
	private boolean connectOnMove;
	private double moveThreshold;
	
	private boolean connectOnRotate;
	private double rotateThreshold;
	
	private long delayAfterJoin;
	private long delayBetweenAttempts;
	
	private String connectType;
	private String[] connectPayloadString;
	
}