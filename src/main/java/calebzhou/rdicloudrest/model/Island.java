package calebzhou.rdicloudrest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


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
    Timestamp ts = Timestamp.valueOf(LocalDateTime.now());

    String crew ;

    public void addMember(String mpid){
        if(crew==null)
            crew="";
        crew+=mpid+",";
    }
    public void removeMember(String mpid){
        crew=crew.replace(mpid+",","");
        /*String newCrew="";
        String[] split = crew.split(",");
        for(String mpidIn:split){
            if(!mpidIn.equals(mpid))
                newCrew+=mpid+",";
        }
        crew=newCrew;*/
    }
    public boolean isMemberExists(String mpid){
        if(crew==null) return false;
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
        x= (int) Double.parseDouble(split[0]);
        y= (int) Double.parseDouble(split[1]);
        z= (int) Double.parseDouble(split[2]);
    }
    public String getLocation(){
        return x + "," + y + "," + z;
    }
}
