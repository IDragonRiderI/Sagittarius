package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.commands.ViaCommandHandler;

import java.util.Collections;
import java.util.List;

public class SagittariusViaCommandHandler extends ViaCommandHandler {

	@Override
	public boolean onCommand(ViaCommandSender sender, String[] args) {
		return false;
	}
	
	@Override
	public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
		return Collections.emptyList();
	}
	
	@Override
	public void registerSubCommand(ViaSubCommand command) {
	
	}
	
	@Override
	public void showHelp(ViaCommandSender sender) {
	
	}
	
	@Override
	public boolean hasSubCommand(String name) {
		return false;
	}
	
}
