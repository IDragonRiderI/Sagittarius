package de.spacepotato.sagittarius.cache;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.network.protocol.play.ServerJoinGamePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPlayerAbilitiesPacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPlayerPositionAndLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerSpawnPositionPacket;
import de.spacepotato.sagittarius.world.BlockPosition;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import lombok.Getter;

@Getter
public class PacketCache {

	private ServerJoinGamePacket joinGame;
	private ServerSpawnPositionPacket spawnPosition;
	private ServerPlayerPositionAndLookPacket positionAndLook;
	private ServerPlayerAbilitiesPacket playerAbilities;
	
	public PacketCache() {
		
	}
	
	public void createPackets() {
		createJoinGame();
		createSpawnPosition();
		createPositionAndLook();
		createPlayerAbilities();
	}
	
	private void createJoinGame() {
		GameMode gameMode = GameMode.SURVIVAL;
		Dimension dimension = Dimension.OVERWORLD;
		Difficulty difficulty = Difficulty.EASY;
		boolean reducedDebugInfo = false;
		joinGame = new ServerJoinGamePacket(0, (byte) gameMode.ordinal(), (byte) dimension.ordinal(), (byte) difficulty.ordinal(), (byte) 1, "default", reducedDebugInfo);
	}
	
	private void createSpawnPosition() {
		int x = 0;
		int y = 100;
		int z = 0;
		spawnPosition = new ServerSpawnPositionPacket(new BlockPosition(x, y, z));
	}
	
	private void createPositionAndLook() {
		double x = 0;
		double y = 100;
		double z = 0;
		float yaw = 0;
		float pitch = 0;
		positionAndLook = new ServerPlayerPositionAndLookPacket(x, y, z, yaw, pitch, (byte) 0);
	}
	
	private void createPlayerAbilities() {
		boolean canFly = false;
		boolean isFlying = false;

		byte flags = 0;
		if (isFlying) flags |= 0x02;
		if (canFly) flags |= 0x04;
		
		float flySpeed = 0.05F;
		float movementSpeed = 0.1F;
		playerAbilities = new ServerPlayerAbilitiesPacket(flags, flySpeed, movementSpeed);
	}
	
}
