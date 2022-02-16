package calebzhou.rdicloudrest.module.island;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class IslandCrew implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @ManyToOne
    @JoinColumn(name = "islandId",nullable = false)
    private Island island;
    @Column
    String mpid;

    public IslandCrew() {
    }
}
