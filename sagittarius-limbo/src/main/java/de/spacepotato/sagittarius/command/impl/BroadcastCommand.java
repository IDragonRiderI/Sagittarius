package de.spacepotato.sagittarius.command.impl;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.command.Command;
import de.spacepotato.sagittarius.command.CommandSender;
import de.spacepotato.sagittarius.entity.Player;

public class BroadcastCommand implements Command {

	@Override
	public String getName() {
		return "broadcast";
	}

	@Override
	public String getDescription() {
		return "Sends a message to all online players.";
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String message = String.join(" ", args);
		synchronized (Sagittarius.getInstance().getPlayers()) {
			for (Player players : Sagittarius.getInstance().getPlayers()) {
				players.sendMessage(message);
			}
		}
		sender.sendMessage("Broadcast message sent: " + message);
	}

}
