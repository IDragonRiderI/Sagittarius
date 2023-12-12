package de.spacepotato.sagittarius.util;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.SagittariusImpl;
import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.world.Location;

public class PlayerMovementTracker {
	
	private final PlayerImpl player;
	
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
	private long nextConnectionAttempt;
	
	public PlayerMovementTracker(PlayerImpl player) {
		this.player = player;
		Location spawn = Sagittarius.getInstance().getConfig().getSpawnPoint();
		x = spawn.getX();
		y = spawn.getY();
		z = spawn.getZ();
		yaw = spawn.getYaw();
		pitch = spawn.getPitch();
		
		nextConnectionAttempt = System.currentTimeMillis() + Sagittarius.getInstance().getConfig().getCheckDelayAfterJoin();
		
	}
	
	public void onMove(double x, double y, double z) {
		if (!Sagittarius.getInstance().getConfig().shouldConnectOnMove()) return;
		double distanceX = x - this.x;
		double distanceY = y - this.y;
		double distanceZ = z - this.z;
		this.x = x;
		this.y = y;
		this.z = z;
		
		double distanceXYZ = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
		if (distanceXYZ > Sagittarius.getInstance().getConfig().getMoveThresholdSquared()) {
			tryConnect();
		}
		
	}
	
	public void onRotate(float yaw, float pitch) {
		if (!Sagittarius.getInstance().getConfig().shouldConnectOnRotate()) return;
		float diffYaw = yaw - this.yaw;
		float diffPitch = pitch = this.pitch;
		
		this.yaw = yaw;
		this.pitch = pitch;
		
		double rotateThreshold = Sagittarius.getInstance().getConfig().getRotateThreshold();
		
		if (Math.abs(diffYaw) > rotateThreshold || Math.abs(diffPitch) > rotateThreshold) {
			tryConnect();
		}
	}
	
	public void tryConnect() {
		if (System.currentTimeMillis() < nextConnectionAttempt) return;
		
		nextConnectionAttempt = System.currentTimeMillis() + Sagittarius.getInstance().getConfig().getCheckDelayBetweenAttempts();
		player.sendPacket(SagittariusImpl.getInstance().getPacketCache().getConnectPacket());
	}
}