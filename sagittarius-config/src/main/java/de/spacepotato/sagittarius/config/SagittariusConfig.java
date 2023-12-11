package de.spacepotato.sagittarius.config;

public class SagittariusConfig implements LimboConfig {

	private final TomlConfigurationFile tomlConfig;
	
	public SagittariusConfig() {
		tomlConfig = TomlConfigurationFile.loadConfig();
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

}
