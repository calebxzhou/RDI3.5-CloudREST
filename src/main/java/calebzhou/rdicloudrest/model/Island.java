package calebzhou.rdicloudrest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;


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

    String crew ;

    public void addMember(String mpid){
        crew+=","+mpid;
    }
    public void removeMember(String mpid){
        String newCrew="";
        String[] split = crew.split(",");
        for(String mpidIn:split){
            if(!mpidIn.equals(mpid))
                newCrew+=mpid+",";
        }
        crew=newCrew;
    }
    public boolean isMemberExists(String mpid){
        return crew.contains(mpid);
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
