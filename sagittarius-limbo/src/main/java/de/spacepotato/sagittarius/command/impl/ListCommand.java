package de.spacepotato.sagittarius.command.impl;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.command.Command;
import de.spacepotato.sagittarius.command.CommandSender;
import de.spacepotato.sagittarius.entity.Player;

public class ListCommand implements Command {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "Displays all online players";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("The following players are online:");
		synchronized (Sagittarius.getInstance().getPlayers()) {
			for (Player player : Sagittarius.getInstance().getPlayers()) {
				sender.sendMessage("- " + player.getName());
			}
		}
	}

}
