package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import de.spacepotato.sagittarius.entity.Player;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class SagittariusCommandSender implements ViaCommandSender {

	private final Player player;
	
	@Override
	public boolean hasPermission(String permission) {
		return false;
	}

	@Override
	public void sendMessage(String msg) {
		player.sendMessage(msg);
	}

	@Override
	public UUID getUUID() {
		return player.getUUID();
	}

	@Override
	public String getName() {
		return player.getName();
	}

}
