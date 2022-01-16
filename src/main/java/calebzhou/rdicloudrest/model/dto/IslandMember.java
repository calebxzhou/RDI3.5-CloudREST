package calebzhou.rdicloudrest.model.dto;

import java.io.Serializable;

public class IslandMember implements Serializable {
    String islandId;
    String memberUuid;

    public IslandMember() {
    }

    public IslandMember(String islandId, String memberUuid) {
        this.islandId = islandId;
        this.memberUuid = memberUuid;
    }

    public void setIslandId(String islandId) {
        this.islandId = islandId;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }
}
