package de.spacepotato.sagittarius.chat;

import lombok.Getter;

@Getter
public class ClickEvent {

	public static enum ClickEventAction {
		OPEN_URL,
		RUN_COMMAND,
		SUGGEST_COMMAND,
		CHANGE_PAGE;
	}
	
	private final ClickEventAction action;
	private final String value;
	
	public ClickEvent(ClickEventAction action, String value) {
		this.action = action;
		this.value = value;
	}
	
}
