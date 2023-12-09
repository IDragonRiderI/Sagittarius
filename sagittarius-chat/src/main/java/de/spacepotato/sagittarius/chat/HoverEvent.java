package de.spacepotato.sagittarius.chat;

import lombok.Getter;

@Getter
public class HoverEvent {

	public static enum HoverEventAction {
		SHOW_TEXT,
		SHOW_ITEM,
		SHOW_ENTITY;
		
	}
	
	private final HoverEventAction action;
	private final Component value;
	
	public HoverEvent(HoverEventAction action, Component value) {
		this.action = action;
		this.value = value;
	}
	
}
