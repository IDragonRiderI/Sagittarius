package de.spacepotato.sagittarius.config.toml;

import lombok.Data;

@Data
public class WorldBorderConfig {

	private double centerX;
	private double centerZ;
	private double radius;
	private String color;
	private int warningTime;
	private int warningBlocks;	
	
}
