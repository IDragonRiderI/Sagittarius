package de.spacepotato.sagittarius;

public enum GameMode {

	SURVIVAL,
	CREATIVE,
	ADVENTURE,
	SPECTATOR,
	;
	
	private final int id;
	
	private GameMode() {
		id = ordinal();
	}
	
	public static GameMode getGameMode(String gameMode) {
		try {
			return GameMode.valueOf(gameMode.toUpperCase());
		} catch(Exception ex) {
			try {
				int id = Integer.parseInt(gameMode);
				for (GameMode gm : values()) {
					if (gm.id == id) return gm;
				}
			} catch(Exception ex2) {
				
			}
		}
		return null;
	}
	
}
