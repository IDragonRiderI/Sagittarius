package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class InternalConfig {

	private int heartbeatDelay;
	private int heartbeatTimeout;
	
}
