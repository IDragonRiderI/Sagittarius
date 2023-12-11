package de.spacepotato.sagittarius.config;

import lombok.Data;

@Data
public class NetworkConfig {

	private String host;
	private int port;
	private boolean nativeNetworking;
	private boolean viaversion;
	
}
