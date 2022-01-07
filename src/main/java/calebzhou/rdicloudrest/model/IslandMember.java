package calebzhou.rdicloudrest.model;

public class IslandMember {
    String islandId;
    String memberUuid;

    public IslandMember setIslandId(String islandId) {
        this.islandId = islandId;
        return this;
    }

    public IslandMember setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
        return this;
    }
}
