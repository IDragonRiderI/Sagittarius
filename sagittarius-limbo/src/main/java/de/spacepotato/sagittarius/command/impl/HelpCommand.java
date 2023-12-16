package de.spacepotato.sagittarius.command.impl;

import java.util.Collection;

import de.spacepotato.sagittarius.command.Command;
import de.spacepotato.sagittarius.command.CommandSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HelpCommand implements Command {

	private final Collection<Command> commands;
	
	@Override
	public String getName() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "Displays the helppage.";
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("All available commands:");
		commands.forEach(command -> {
			sender.sendMessage("- " + command.getName() + ": " + command.getDescription());
		});
	}
	
}
