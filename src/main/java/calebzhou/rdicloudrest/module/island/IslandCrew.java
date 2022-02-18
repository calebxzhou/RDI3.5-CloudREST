package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.utils.RandomUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class IslandCrew implements Serializable {
    @Id
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "island")
    private Island island;

    @Column
    String mpid;



    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public String getMpid() {
        return mpid;
    }

    public void setMpid(String mpid) {
        this.mpid = mpid;
    }

    public IslandCrew() {
    }
    public IslandCrew(Island island, String mpid) {
        this.id= RandomUtils.getRandomId();
        this.island = island;
        this.mpid = mpid;
    }
}
