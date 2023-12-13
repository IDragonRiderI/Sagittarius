package de.spacepotato.sagittarius.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClickEvent {

	public enum ClickEventAction {
		OPEN_URL,
		RUN_COMMAND,
		SUGGEST_COMMAND,
		CHANGE_PAGE;
	}
	
	private final ClickEventAction action;
	private final String value;

}
