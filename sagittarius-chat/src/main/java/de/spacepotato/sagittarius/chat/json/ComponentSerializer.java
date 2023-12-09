package de.spacepotato.sagittarius.chat.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.spacepotato.sagittarius.chat.ChatComponent;
import de.spacepotato.sagittarius.chat.ClickEvent;
import de.spacepotato.sagittarius.chat.Component;
import de.spacepotato.sagittarius.chat.ComponentColor;
import de.spacepotato.sagittarius.chat.ComponentStyle;
import de.spacepotato.sagittarius.chat.HoverEvent;

public class ComponentSerializer implements JsonSerializer<Component>, JsonDeserializer<Component> {

	public static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(Component.class, new ComponentSerializer()).create();
	
	@Override
	public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		Component result = null;
		if (object.has("text")) {
			result = new ChatComponent(object.get("text").getAsString());
		}
		
		if (result == null) return result;
		byte flags = 0;
		for (ComponentStyle style : ComponentStyle.values()) {
			String lowerName = style.name().toLowerCase();
			boolean hasStyle = object.has(lowerName) && object.get(lowerName).getAsBoolean();
			if (hasStyle) flags |= style.getShift();
		}
		result.setStyle(flags);
		if (object.has("color")) {
			result.setColor(ComponentColor.valueOf(object.get("color").getAsString().toUpperCase()));
		}
		if (object.has("clickEvent")) {
			result.setClickEvent(context.deserialize(object.get("clickEvent"), ClickEvent.class));
		}
		if (object.has("hoverEvent")) {
			result.setHoverEvent(context.deserialize(object.get("hoverEvent"), HoverEvent.class));
		}
		if (object.has("extra")) {
			// Make a modifiable list
			result.setExtra(new ArrayList<>(Arrays.asList(context.deserialize(object.get("extra"), Component[].class))));
		}
		
		return result;
	}

	@Override
	public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		if (src instanceof ChatComponent) {
			ChatComponent chat = (ChatComponent) src;
			object.addProperty("text", chat.getText());
		}
		
		// Italic, bold, strikethrough, ...
		for (ComponentStyle style : ComponentStyle.values()) {
			if (style.doesApply(src.getStyle())) {
				object.addProperty(style.name().toLowerCase(), true);
			}
		}
		
		if (src.getColor() != null) object.addProperty("color", src.getColor().toName());
		if (src.getClickEvent() != null) object.add("clickEvent", context.serialize(src.getClickEvent()));
		if (src.getHoverEvent() != null) object.add("hoverEvent", context.serialize(src.getHoverEvent()));
		if (src.getExtra() != null) object.add("extra", context.serialize(src.getExtra()));
		
		return object;
	}

}