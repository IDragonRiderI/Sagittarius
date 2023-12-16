package de.spacepotato.sagittarius.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.command.impl.BroadcastCommand;
import de.spacepotato.sagittarius.command.impl.HelpCommand;
import de.spacepotato.sagittarius.command.impl.ListCommand;
import de.spacepotato.sagittarius.command.impl.ReloadCommand;
import de.spacepotato.sagittarius.command.impl.StopCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleCommandHandler {

	private Thread thread;
	private boolean running;
	private HashMap<String, Command> commands;
	
	public ConsoleCommandHandler() {
		commands = new HashMap<>();
		registerCommands();
	}
	
	public void startCommandThread() {
		if (running) return;
		running = true;
		thread = new Thread(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				String data = null;
				while (running && (data = reader.readLine()) != null) {
					try {
						runCommand(Sagittarius.getInstance().getConsole(), data);
					} catch(Exception ex) {
						log.error("Unable to parse command: " + data + "! ", ex);
					}
				}
			} catch(Exception ex) {
				log.error("Unable to listen for commands! ", ex);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	public void stopCommandThread() {
		running = false;
	}
	
	private void registerCommands() {
		registerCommand(new StopCommand());
		registerCommand(new HelpCommand(this.commands.values()));
		registerCommand(new ReloadCommand());
		registerCommand(new ListCommand());
		registerCommand(new BroadcastCommand());
	}
	
	private void registerCommand(Command command) {
		this.commands.put(command.getName(), command);
	}
	
	private void runCommand(CommandSender sender, String command) {
		String commandName = command;
		String[] args = new String[0];
		if (command.contains(" ")) {
			String[] array = command.split(" ", 2);
			commandName = array[0];
			args = array[1].split(" ");
		}
		commandName = commandName.toLowerCase();
		
		Command commandObject = this.commands.get(commandName);
		if (commandObject == null) {
			sender.sendMessage("Unknown command.");
			return;
		}
		commandObject.execute(sender, args);
	}
	
}
