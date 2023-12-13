package de.spacepotato.sagittarius.chat;

import de.spacepotato.sagittarius.chat.json.ComponentSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Component {
	
	private byte style;
	private ComponentColor color;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private List<Component> extra;
	
	@Tolerate
	public void setStyle(ComponentStyle... styles) {
		style = ComponentStyle.toByte(styles);
	}
	
	public void addExtra(Component component) {
		if (extra == null) {
            extra = new ArrayList<>();
        }
		extra.add(component);
	}
	
	public String toJson() {
		return ComponentSerializer.GSON.toJson(this);
	}
}
