package de.spacepotato.sagittarius.chat;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ComponentStyle {

	BOLD,
	ITALIC,
	UNDERLINED,
	STRIKETHROUGH,
	OBFUSCATED,
	;
	
	private final byte shift;
	
	ComponentStyle() {
		shift = (byte) (1 << ordinal());
	}
	
	public boolean doesApply(byte flags) {
		return (flags & shift) == shift;
	}

	public static List<ComponentStyle> fromByte(byte flags) {
		List<ComponentStyle> styles = new ArrayList<>();
		for (ComponentStyle style : values()) {
			if (style.doesApply(flags)) {
                styles.add(style);
            }
		}
		return styles;
	}
	
	public static byte toByte(ComponentStyle... styles) {
		byte flags = 0;
		for (ComponentStyle style : styles) {
			flags |= style.shift;
		}
		return flags;
	}
	
}
