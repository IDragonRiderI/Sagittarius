package de.spacepotato.sagittarius.config;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.world.Biome;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import de.spacepotato.sagittarius.world.Location;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
public class SagittariusConfig implements LimboConfig {

	private TomlConfigurationFile tomlConfig;
	private Location spawnPoint;
	private GameMode gameMode;
	private Optional<GameMode> tabGameMode;
	private Difficulty difficulty;
	private Biome biome;
	private Dimension dimension;
	private double moveThresholdSquared;
	private byte[] connectPayload;
	
	public SagittariusConfig() {
		reload();
	}
	
	private void reload() {
		tomlConfig = TomlConfigurationFile.loadConfig();
		spawnPoint = new Location(tomlConfig.getWorld().getSpawnX(), tomlConfig.getWorld().getSpawnY(), tomlConfig.getWorld().getSpawnZ(), tomlConfig.getWorld().getYaw(), tomlConfig.getWorld().getPitch());
		gameMode = GameMode.getGameMode(tomlConfig.getPlayer().getGamemode());
		tabGameMode = Optional.ofNullable(GameMode.getGameMode(tomlConfig.getPlayer().getTablistGamemode()));
		biome = Biome.valueOf(tomlConfig.getWorld().getBiome().toUpperCase());
		difficulty = Difficulty.valueOf(tomlConfig.getWorld().getDifficulty().toUpperCase());
		dimension = Dimension.valueOf(tomlConfig.getWorld().getDimension().toUpperCase());
		moveThresholdSquared = Math.pow(getMoveThreshold(), 2);
		
		if (tomlConfig.getConnect().getConnectType().equalsIgnoreCase("string")) {
			try (ByteArrayOutputStream out = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(out)) {
				for (String s : tomlConfig.getConnect().getConnectPayloadString()) {
					dos.writeUTF(s);
				}
				connectPayload = out.toByteArray();
			} catch(Exception ex) {
				log.error("Could not create plugin message from string! ", ex);
			}
		} else if(tomlConfig.getConnect().getConnectType().startsWith("file:")) {
			String fileName = tomlConfig.getConnect().getConnectType().substring("file:".length());
			File file = new File(fileName);
			try {
				connectPayload = Files.readAllBytes(file.toPath());
			} catch(Exception ex) {
				log.error("Could not create plugin message from file! ", ex);
			}
		} else {
			log.warn("No valid connect type found: " + tomlConfig.getConnect().getConnectType());
		}
	}
	
	@Override
	public String getHost() {
		return tomlConfig.getNetwork().getHost();
	}

	@Override
	public int getPort() {
		return tomlConfig.getNetwork().getPort();
	}

	@Override
	public boolean shouldUseViaVersion() {
		return tomlConfig.getNetwork().isViaversion();
	}

	@Override
	public boolean shouldUseNativeNetworking() {
		return tomlConfig.getNetwork().isNativeNetworking();
	}
	
	@Override
	public int getNettyThreads() {
		return tomlConfig.getNetwork().getNettyThreads();
	}

	@Override
	public Location getSpawnPoint() {
		return spawnPoint;
	}

	@Override
	public Difficulty getDifficulty() {
		return difficulty;
	}

	@Override
	public Dimension getDimension() {
		return dimension;
	}

	@Override
	public GameMode getGameMode() {
		return gameMode;
	}

	@Override
	public Optional<GameMode> getTabGameMode() {
		return tabGameMode;
	}

	@Override
	public boolean canFly() {
		return tomlConfig.getPlayer().isAllowFly();
	}

	@Override
	public boolean isAutoFly() {
		return tomlConfig.getPlayer().isAutoFly();
	}

	@Override
	public float getFlySpeed() {
		return tomlConfig.getPlayer().getFlySpeed();
	}

	@Override
	public int getMaxKeepAlivePackets() {
		return tomlConfig.getInternal().getHeartbeatTimeout();
	}

	@Override
	public int getKeepAliveDelay() {
		return tomlConfig.getInternal().getHeartbeatDelay();
	}

	@Override
	public boolean isReducedDebugInfo() {
		return tomlConfig.getPlayer().isHideDebugInfo();
	}

	@Override
	public Biome getBiome() {
		return biome;
	}

	@Override
	public boolean shouldConnectOnMove() {
		return tomlConfig.getConnect().isConnectOnMove();
	}

	@Override
	public double getMoveThreshold() {
		return tomlConfig.getConnect().getMoveThreshold();
	}

	@Override
	public double getMoveThresholdSquared() {
		return moveThresholdSquared;
	}

	@Override
	public boolean shouldConnectOnRotate() {
		return tomlConfig.getConnect().isConnectOnRotate();
	}

	@Override
	public double getRotateThreshold() {
		return tomlConfig.getConnect().getRotateThreshold();
	}

	@Override
	public long getCheckDelayAfterJoin() {
		return tomlConfig.getConnect().getDelayAfterJoin();
	}

	@Override
	public long getCheckDelayBetweenAttempts() {
		return tomlConfig.getConnect().getDelayBetweenAttempts();
	}

	@Override
	public byte[] getConnectPayload() {
		return connectPayload;
	}

	@Override
	public boolean shouldSendJoinMessage() {
		return tomlConfig.getMessages().isSendJoinMessage();
	}

	@Override
	public String getJoinMessage() {
		return tomlConfig.getMessages().getJoinMessage();
	}

	@Override
	public boolean shouldSendActionbar() {
		return tomlConfig.getMessages().isSendActionbar();
	}

	@Override
	public String getActionbarMessage() {
		return tomlConfig.getMessages().getActionbarMessage();
	}

	@Override
	public int getActionbarIntervalTicks() {
		return tomlConfig.getMessages().getActionbarIntervalTicks();
	}

	@Override
	public boolean shouldSendBroadcasts() {
		return tomlConfig.getMessages().isSendBroadcast();
	}

	@Override
	public String getBroadcastMessage() {
		return tomlConfig.getMessages().getBroadcastMessage();
	}

	@Override
	public int getBroadcastIntervalTicks() {
		return tomlConfig.getMessages().getBroadcastIntervalTicks();
	}

}
