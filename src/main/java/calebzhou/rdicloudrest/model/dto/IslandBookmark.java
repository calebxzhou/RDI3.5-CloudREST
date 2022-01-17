package calebzhou.rdicloudrest.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class IslandBookmark implements Serializable {
    String islandId;
    String creatorPid;
    String markName;
    String location;
    Timestamp createTime;

    public IslandBookmark() {
    }

    public IslandBookmark(String islandId, String creatorPid, String markName, String location, Timestamp createTime) {
        this.islandId = islandId;
        this.creatorPid = creatorPid;
        this.markName = markName;
        this.location = location;
        this.createTime = createTime;
    }

    public String getIslandId() {
        return islandId;
    }

    public void setIslandId(String islandId) {
        this.islandId = islandId;
    }

    public String getCreatorPid() {
        return creatorPid;
    }

    public void setCreatorPid(String creatorPid) {
        this.creatorPid = creatorPid;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
