package de.spacepotato.sagittarius.chat.json;

import com.google.gson.*;
import de.spacepotato.sagittarius.chat.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class ComponentSerializer implements JsonSerializer<Component>, JsonDeserializer<Component> {

	public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Component.class, new ComponentSerializer()).create();

	private static final String ATTRIBUTE_TEXT = "text";
	private static final String ATTRIBUTE_COLOR = "color";
	private static final String ATTRIBUTE_CLICK_EVENT = "clickEvent";
	private static final String ATTRIBUTE_HOVER_EVENT = "hoverEvent";
	private static final String ATTRIBUTE_EXTRA = "extra";
	
	@Override
	public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		Component result = null;
		if (object.has(ATTRIBUTE_TEXT)) {
			result = new ChatComponent(object.get(ATTRIBUTE_TEXT).getAsString());
		}

		if (result == null) {
            return null;
        }
		byte flags = 0;
		for (ComponentStyle style : ComponentStyle.values()) {
			String lowerName = style.name().toLowerCase();
			boolean hasStyle = object.has(lowerName) && object.get(lowerName).getAsBoolean();
			if (hasStyle) {
                flags |= style.getShift();
            }
		}
		result.setStyle(flags);
		if (object.has("color")) {
			result.setColor(ComponentColor.valueOf(object.get(ATTRIBUTE_COLOR).getAsString().toUpperCase()));
		}
		if (object.has(ATTRIBUTE_CLICK_EVENT)) {
			result.setClickEvent(context.deserialize(object.get(ATTRIBUTE_CLICK_EVENT), ClickEvent.class));
		}
		if (object.has(ATTRIBUTE_HOVER_EVENT)) {
			result.setHoverEvent(context.deserialize(object.get(ATTRIBUTE_HOVER_EVENT), HoverEvent.class));
		}
		if (object.has(ATTRIBUTE_EXTRA)) {
			// Make a modifiable list
			result.setExtra(new ArrayList<>(Arrays.asList(context.deserialize(object.get(ATTRIBUTE_EXTRA), Component[].class))));
		}

		return result;
	}

	@Override
	public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		if (src instanceof ChatComponent) {
			ChatComponent chat = (ChatComponent) src;
			object.addProperty(ATTRIBUTE_TEXT, chat.getText());
		}

		// Italic, bold, strikethrough, ...
		for (ComponentStyle style : ComponentStyle.values()) {
			if (style.doesApply(src.getStyle())) {
				object.addProperty(style.name().toLowerCase(), true);
			}
		}

		if (src.getColor() != null) {
            object.addProperty(ATTRIBUTE_COLOR, src.getColor().toName());
        }
		if (src.getClickEvent() != null) {
            object.add(ATTRIBUTE_CLICK_EVENT, context.serialize(src.getClickEvent()));
        }
		if (src.getHoverEvent() != null) {
            object.add(ATTRIBUTE_HOVER_EVENT, context.serialize(src.getHoverEvent()));
        }
		if (src.getExtra() != null) {
            object.add(ATTRIBUTE_EXTRA, context.serialize(src.getExtra()));
        }

		return object;
	}

}
