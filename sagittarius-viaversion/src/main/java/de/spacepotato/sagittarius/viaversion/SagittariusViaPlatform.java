package de.spacepotato.sagittarius.viaversion;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.libs.gson.JsonObject;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.entity.Player;
import de.spacepotato.sagittarius.log.JULLoggerBridge;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusCommandSender;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusPlatformTask;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusViaApi;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusViaCommandHandler;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusViaConfig;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusViaInjector;
import de.spacepotato.sagittarius.viaversion.platform.SagittariusViaLoader;


public class SagittariusViaPlatform implements ViaPlatform<Player>{

	private static final SagittariusViaPlatform INSTANCE = new SagittariusViaPlatform();
	private static final String PLUGIN_VERSION = "4.9.2";

	public static void init() {
		if (!Sagittarius.getInstance().getConfig().shouldUseViaVersion()) return;
		Via.init(ViaManagerImpl.builder()
				.platform(INSTANCE)
				.commandHandler(new SagittariusViaCommandHandler())
				.injector(new SagittariusViaInjector())
				.loader(new SagittariusViaLoader())
				.build());
	}
	
	public static void load() {
		if (!Sagittarius.getInstance().getConfig().shouldUseViaVersion()) return;
		((ViaManagerImpl) Via.getManager()).init();
	}
	
	public static void finishStartup() {
		if (!Sagittarius.getInstance().getConfig().shouldUseViaVersion()) return;
		((ViaManagerImpl) Via.getManager()).onServerLoaded();		
	}
	
	public static void destroy() {
		if (!Sagittarius.getInstance().getConfig().shouldUseViaVersion()) return;
		((ViaManagerImpl) Via.getManager()).destroy();

	}
	
	private final Logger logger;
	private final File dataFolder;
	private final SagittariusViaApi api;
	private final SagittariusViaConfig config;
	
	public SagittariusViaPlatform() {
		logger = new JULLoggerBridge();
		dataFolder = new File("viaversion");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		File configFile = new File(dataFolder, "via.yml");
		
		api = new SagittariusViaApi();
		config = new SagittariusViaConfig(configFile);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getPlatformName() {
		return Sagittarius.getInstance().getName();
	}

	@Override
	public String getPlatformVersion() {
		return Sagittarius.getInstance().getVersion();
	}

	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
	}

	@Override
	public SagittariusPlatformTask runAsync(Runnable runnable) {
		return new SagittariusPlatformTask(Sagittarius.getInstance().getScheduler().runAsync(runnable));
	}

	@Override
	public SagittariusPlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
		return new SagittariusPlatformTask(Sagittarius.getInstance().getScheduler().repeatAsync(runnable, ticks, ticks));
	}

	@Override
	public SagittariusPlatformTask runSync(Runnable runnable) {
		return new SagittariusPlatformTask(Sagittarius.getInstance().getScheduler().run(runnable));
	}

	@Override
	public SagittariusPlatformTask runSync(Runnable runnable, long delay) {
		return new SagittariusPlatformTask(Sagittarius.getInstance().getScheduler().runLater(runnable, delay));
	}

	@Override
	public SagittariusPlatformTask runRepeatingSync(Runnable runnable, long period) {
		return new SagittariusPlatformTask(Sagittarius.getInstance().getScheduler().repeat(runnable, period, period));
	}

	@Override
	public ViaCommandSender[] getOnlinePlayers() {
		synchronized (Sagittarius.getInstance().getPlayers()) {
			return Sagittarius.getInstance().getPlayers().stream().map(SagittariusCommandSender::new).toArray(SagittariusCommandSender[]::new);
		}
	}

	@Override
	public void sendMessage(UUID uuid, String message) {
		synchronized (Sagittarius.getInstance().getPlayers()) {
			for (Player players : Sagittarius.getInstance().getPlayers()) {
				if (players.getUUID().equals(uuid)) {
					players.sendMessage(message);
					return;
				}
			}
		}
	}

	@Override
	public boolean kickPlayer(UUID uuid, String message) {
		Player target = null;
		synchronized (Sagittarius.getInstance().getPlayers()) {
			for (Player players : Sagittarius.getInstance().getPlayers()) {
				if (players.getUUID().equals(uuid)) {
					target = players;
					break;
				}
			}
		}

		if (target != null) {
            target.kick(message);
        }
		return target != null;
	}

	@Override
	public boolean isPluginEnabled() {
		return true;
	}

	@Override
	public ViaAPI<Player> getApi() {
		return api;
	}

	@Override
	public ViaVersionConfig getConf() {
		return config;
	}

	@Override
	public File getDataFolder() {
		return dataFolder;
	}

	@Override
	public void onReload() {
		
	}

	@Override
	public JsonObject getDump() {
		// TODO: properly implement dumps
		return new JsonObject();
	}

	@Override
	public boolean hasPlugin(String name) {
		// We don't have plugins...
		return false;
	}
	
}
