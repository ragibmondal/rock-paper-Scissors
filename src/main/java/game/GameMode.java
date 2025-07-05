package game;

/**
 * Enumeration representing different game modes
 */
public enum GameMode {
    PLAYER_VS_COMPUTER("Player vs Computer", "PvC"),
    PLAYER_VS_PLAYER("Player vs Player", "PvP");
    
    private final String displayName;
    private final String shortCode;
    
    GameMode(String displayName, String shortCode) {
        this.displayName = displayName;
        this.shortCode = shortCode;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getShortCode() {
        return shortCode;
    }
}
