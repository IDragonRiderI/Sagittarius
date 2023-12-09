package de.spacepotato.sagittarius.chat;

public enum ComponentColor {

	BLACK("§0"),
	DARK_BLUE("§1"),
	DARK_GREEN("§2"),
	DARK_AQUA("§3"),
	DARK_RED("§4"),
	DARK_PURPLE("§5"),
	GOLD("§6"),
	GRAY("§7"),
	DARK_GRAY("§8"),
	BLUE("§9"),
	GREEN("§a"),
	AQUA("§b"),
	RED("§c"),
	LIGHT_PURPLE("§d"),
	YELLOW("§e"),
	WHITE("§f");
	
	private final String code;
	private final String lowerName;
	
	private ComponentColor(String code) {
		this.code = code;
		this.lowerName = name().toLowerCase();
	}
	
	public static ComponentColor fromCode(String code) {
		for(ComponentColor c : values()) {
			if(c.code.equals(code)) {
				return c;
			}
		}
		return null;
	}
	
	public String toName() {
		return lowerName;
	}

	
}
