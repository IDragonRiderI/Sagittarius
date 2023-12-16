package de.spacepotato.sagittarius.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleCommandSender implements CommandSender {
	
	@Override
	public void sendMessage(String message) {
		log.info(message);
	}
	
}
