package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.configuration.AbstractViaConfig;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SagittariusViaConfig extends AbstractViaConfig {

    private final List<String> unsupportedOptions = Arrays.asList(
    		"bungee-ping-interval", "bungee-ping-save", "bungee-servers", // BungeeCord
    		"velocity-ping-interval", "velocity-ping-save", "velocity-servers", // Velocity
    		"reload-disconnect-msg", "prevent-collision", "auto-team", "shield-blocking", "no-delay-shield-blocking", "show-shield-when-sword-in-hand",
    		"simulate-pt", "nms-player-ticking", "use-new-deathmessages", "item-cache", "minimize-cooldown",
            "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method");

    public SagittariusViaConfig(File config) {
        super(config);
        reload();
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return unsupportedOptions;
    }
	
}
