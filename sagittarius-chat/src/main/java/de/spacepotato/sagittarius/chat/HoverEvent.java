package de.spacepotato.sagittarius.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HoverEvent {

	public enum HoverEventAction {
		SHOW_TEXT,
		SHOW_ITEM,
		SHOW_ENTITY;
	}
	
	private final HoverEventAction action;
	private final Component value;
	
}
