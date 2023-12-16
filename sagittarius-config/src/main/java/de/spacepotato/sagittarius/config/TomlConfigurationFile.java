package de.spacepotato.sagittarius.config;

import com.moandjiezana.toml.Toml;
import de.spacepotato.sagittarius.config.toml.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Getter
public class TomlConfigurationFile {

	public static TomlConfigurationFile loadConfig() {
		File configFile = new File("config.toml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				log.error("Failed to create config file! ", e);
			}
			try (InputStream in = TomlConfigurationFile.class.getResourceAsStream("/config.toml")) {
				Files.copy(in, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				log.error("Failed to copy config file! ", e);
			}
        }
		
		return new Toml().read(configFile).to(TomlConfigurationFile.class);		
	}
	
	private NetworkConfig network;
	private PlayerConfig player;
	private InternalConfig internal;
	private WorldConfig world;
	private ConnectConfig connect;
	private MessagesConfig messages;
	private WorldBorderConfig border;
	
}
