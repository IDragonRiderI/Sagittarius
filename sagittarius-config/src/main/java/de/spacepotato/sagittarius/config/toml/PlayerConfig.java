package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class PlayerConfig {

	private String gamemode;
	private String tablistGamemode;
	private boolean allowFly;
	private boolean autoFly;
	private float flySpeed;
	private String defaultLanguage;
	private boolean hideDebugInfo;
	
}
