package calebzhou.rdicloudrest.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class IslandCrew implements Serializable {
    @Id @GeneratedValue
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew")
    @ToString.Exclude
    Island iid;

    @Column
    String mpid;

    public IslandCrew(Island is, String mpid) {
        this.iid=is;this.mpid=mpid;
    }
}
