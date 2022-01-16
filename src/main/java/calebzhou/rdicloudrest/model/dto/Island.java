package calebzhou.rdicloudrest.model.dto;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.utils.TimeUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Island implements Serializable {
    String islandId;
    String ownerUuid;
    String location;
    Timestamp createTime;
    public Island() {
    }

    public Island(String islandId, String ownerUuid, CoordLocation location) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.location = location.toString();
        this.createTime = TimeUtils.getNow();
    }

    public Island(String islandId, String ownerUuid, String location,Timestamp time) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.location = location;
        this.createTime = time;
    }
    public String getIslandId() {
        return islandId;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public String getLocation() {
        return location;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
