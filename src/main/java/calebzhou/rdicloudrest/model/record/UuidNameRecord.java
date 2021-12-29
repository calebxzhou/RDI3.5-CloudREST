package calebzhou.rdicloudrest.model.record;

import java.io.Serializable;

public class UuidNameRecord implements Serializable {
    String playerUuid;
    String playerName;

    public String getPlayerUuid() {
        return playerUuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UuidNameRecord(String playerUuid, String playerName) {
        this.playerUuid = playerUuid;
        this.playerName = playerName;
    }
}
