package de.spacepotato.sagittarius;

import lombok.Getter;

@Getter
public enum GameMode {

    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR,
    ;

    private final int id;

    GameMode() {
        id = ordinal();
    }

    public static GameMode getGameMode(String gameMode) {
        try {
            return getGameMode(Integer.parseInt(gameMode));
        } catch (NumberFormatException ignored) {}
        
        try {
            return GameMode.valueOf(gameMode.toUpperCase());
        } catch (IllegalArgumentException ignored) {}
        
        return null;
    }

    public static GameMode getGameMode(int id) {
        for (GameMode gm : values()) {
            if (gm.id == id) {
                return gm;
            }
        }
        return null;
    }

}
