package de.spacepotato.sagittarius.command;

public interface Command {

	String getName();
	
	String getDescription();
	
	void execute(CommandSender sender, String[] args);
	
}
