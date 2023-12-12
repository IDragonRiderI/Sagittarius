package de.spacepotato.sagittarius.cache;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.network.protocol.PacketContainer;
import de.spacepotato.sagittarius.network.protocol.play.ServerJoinGamePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPlayerAbilitiesPacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPlayerPositionAndLookPacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerPluginMessagePacket;
import de.spacepotato.sagittarius.network.protocol.play.ServerSpawnPositionPacket;
import de.spacepotato.sagittarius.world.BlockPosition;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import de.spacepotato.sagittarius.world.Location;
import lombok.Getter;

@Getter
public class PacketCache {

	private PacketContainer joinGame;
	private PacketContainer spawnPosition;
	private PacketContainer positionAndLook;
	private PacketContainer playerAbilities;
	private PacketContainer connectPacket;
	
	public PacketCache() {
		
	}
	
	public void createPackets() {
		createJoinGame();
		createSpawnPosition();
		createPositionAndLook();
		createPlayerAbilities();
		createConnectPacket();
	}
	
	private void createJoinGame() {
		GameMode gameMode = Sagittarius.getInstance().getConfig().getGameMode();
		Dimension dimension = Sagittarius.getInstance().getConfig().getDimension();
		Difficulty difficulty = Sagittarius.getInstance().getConfig().getDifficulty();
		boolean reducedDebugInfo = Sagittarius.getInstance().getConfig().isReducedDebugInfo();
		joinGame = new PacketContainer(new ServerJoinGamePacket(0, (byte) gameMode.getId(), (byte) dimension.getId(), (byte) difficulty.ordinal(), (byte) 1, "default", reducedDebugInfo));
	}
	
	private void createSpawnPosition() {
		Location spawn = Sagittarius.getInstance().getConfig().getSpawnPoint();
		int x = (int) Math.floor(spawn.getX());
		int y = (int) Math.floor(spawn.getY());
		int z = (int) Math.floor(spawn.getZ());
		spawnPosition = new PacketContainer(new ServerSpawnPositionPacket(new BlockPosition(x, y, z)));
	}
	
	private void createPositionAndLook() {
		Location spawn = Sagittarius.getInstance().getConfig().getSpawnPoint();
		double x = spawn.getX();
		double y = spawn.getY();
		double z = spawn.getZ();
		float yaw = spawn.getYaw();
		float pitch = spawn.getPitch();
		positionAndLook = new PacketContainer(new ServerPlayerPositionAndLookPacket(x, y, z, yaw, pitch, (byte) 0));
	}
	
	private void createPlayerAbilities() {
		boolean canFly = Sagittarius.getInstance().getConfig().canFly();
		boolean isFlying = Sagittarius.getInstance().getConfig().isAutoFly();

		byte flags = 0;
		if (isFlying) flags |= 0x02;
		if (canFly) flags |= 0x04;
		
		float flySpeed = Sagittarius.getInstance().getConfig().getFlySpeed();
		float movementSpeed = 0.1F;
		playerAbilities = new PacketContainer(new ServerPlayerAbilitiesPacket(flags, flySpeed, movementSpeed));
	}
	
	private void createConnectPacket() {
		connectPacket = new PacketContainer(new ServerPluginMessagePacket("BungeeCord", Sagittarius.getInstance().getConfig().getConnectPayload()));
	}
	
}
