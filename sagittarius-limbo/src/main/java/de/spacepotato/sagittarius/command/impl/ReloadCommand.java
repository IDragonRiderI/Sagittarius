package de.spacepotato.sagittarius.command.impl;

import de.spacepotato.sagittarius.SagittariusImpl;
import de.spacepotato.sagittarius.command.Command;
import de.spacepotato.sagittarius.command.CommandSender;

public class ReloadCommand implements Command {

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Reloads the server's configuration and world.";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		SagittariusImpl.getInstance().reload();
	}

}
