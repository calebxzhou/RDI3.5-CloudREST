package calebzhou.rdi.microservice.model;

import calebzhou.rdi.microservice.utils.RdiSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
public class Island2 implements Serializable {
    public int iid;
    public String pid;
    public Timestamp ts;
    public Island2Loca loca;
    public List<Island2Crew> crews;

    @Override
    public String toString() {
        return RdiSerializer.GSON.toJson(this);
    }
}
