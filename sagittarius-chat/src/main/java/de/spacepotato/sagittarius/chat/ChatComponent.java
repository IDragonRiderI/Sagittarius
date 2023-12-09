package de.spacepotato.sagittarius.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatComponent extends Component {

	private String text;
	
	public ChatComponent(String text) {
		this.text = text;
	}
	
}
