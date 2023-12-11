package de.spacepotato.sagittarius.world;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Location {

	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
}
