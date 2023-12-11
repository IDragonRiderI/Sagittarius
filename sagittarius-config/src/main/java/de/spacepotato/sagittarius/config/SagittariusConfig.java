package de.spacepotato.sagittarius.config;

import java.util.Optional;

import de.spacepotato.sagittarius.GameMode;
import de.spacepotato.sagittarius.world.Biome;
import de.spacepotato.sagittarius.world.Difficulty;
import de.spacepotato.sagittarius.world.Dimension;
import de.spacepotato.sagittarius.world.Location;

public class SagittariusConfig implements LimboConfig {

	private TomlConfigurationFile tomlConfig;
	private Location spawnPoint;
	private GameMode gameMode;
	private Optional<GameMode> tabGameMode;
	private Difficulty difficulty;
	private Biome biome;
	private Dimension dimension;
	
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

}
