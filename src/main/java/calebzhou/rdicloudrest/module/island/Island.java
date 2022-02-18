package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.utils.TimeUtils;
import com.google.gson.Gson;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
@Table
public class Island implements Serializable {
    @Id @Column
    String islandId;
    @Column
    String ownerUuid;
    @Column
    String location;
    @Column
    Timestamp createTime;
    @OneToMany(mappedBy = "island",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<IslandCrew> members = new HashSet<>();

    public void addMember(IslandCrew member){
        members.add(member);
        member.setIsland(this);
    }
    public void removeMember(IslandCrew member){
        members.remove(member);
        member.setIsland(null);
    }
    public boolean isMemberExists(String mpid){
        AtomicBoolean exists= new AtomicBoolean(false);
        members.forEach(islandCrew -> {
            if (islandCrew.getMpid().equalsIgnoreCase(mpid)) {
                exists.set(true);
            }
        });
        return exists.get();
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

    public void setIslandId(String islandId) {
        this.islandId = islandId;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    public Island() {
    }

    public Island(String islandId, String ownerUuid, CoordLocation location) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.location = location.toString();
        this.createTime = TimeUtils.getNow();
    }
}
