package de.spacepotato.sagittarius.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JULLoggerBridge extends Logger {
	
	public JULLoggerBridge() {
		super("Sagittarius", null);
	}
	
	@Override
	public void log(LogRecord record) {
		if (record.getLevel() == Level.INFO) {
			log.info(record.getMessage(), record.getParameters());
		} else if(record.getLevel() == Level.WARNING) {
			log.warn(record.getMessage(), record.getParameters());
		} else if(record.getLevel() == Level.SEVERE) {
			log.error(record.getMessage(), record.getParameters());
		}
	}
		
	
}
