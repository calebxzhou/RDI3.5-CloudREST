package calebzhou.rdicloudrest.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Island implements Serializable {
    @Id @GeneratedValue
    int iid;
    String pid;
    int x;
    int y;
    int z;
    Timestamp ts;
    @OneToMany(mappedBy = "iid",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<IslandCrew> crew = new HashSet<>();

    public void addMember(IslandCrew member){
        crew.add(member);
        member.setIid(this);
    }
    public void removeMember(IslandCrew member){
        crew.remove(member);
        member.setIid(null);
    }
    public boolean isMemberExists(String mpid){
        return crew.stream().anyMatch(crew->crew.getMpid().equals(mpid));
        /*AtomicBoolean exists= new AtomicBoolean(false);
        crew.forEach(islandCrew -> {
            if (islandCrew.getMpid().equalsIgnoreCase(mpid)) {
                exists.set(true);
            }
        });
        return exists.get();*/
    }

    public void setLocation(String xyz) {
        String[] split = xyz.split(",");
        x= Integer.parseInt(split[0]);
        y= Integer.parseInt(split[1]);
        z= Integer.parseInt(split[2]);
    }
    public String getLocation(){
        return x + "," + y + "," + z;
    }
}
