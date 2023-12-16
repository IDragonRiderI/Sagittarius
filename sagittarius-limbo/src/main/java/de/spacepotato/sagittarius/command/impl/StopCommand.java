package de.spacepotato.sagittarius.command.impl;

import de.spacepotato.sagittarius.SagittariusImpl;
import de.spacepotato.sagittarius.command.Command;
import de.spacepotato.sagittarius.command.CommandSender;

public class StopCommand implements Command {

	@Override
	public String getName() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "Stops the server.";
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("Shutting down...");
		SagittariusImpl.getInstance().shutdown();
	}

}
