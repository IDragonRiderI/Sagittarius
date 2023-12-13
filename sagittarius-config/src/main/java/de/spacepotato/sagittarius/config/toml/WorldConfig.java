package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class WorldConfig {

	private double spawnX;
	private double spawnY;
	private double spawnZ;
	private float yaw;
	private float pitch;
	private String biome;
	private String dimension;
	private String difficulty;
}
