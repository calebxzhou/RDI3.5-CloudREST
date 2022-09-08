package calebzhou.rdi.microservice.model;

import calebzhou.rdi.microservice.utils.RdiSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class Island2Crew implements Serializable{
    transient int id;
    String pid;
    int iid;
    @Override
    public String toString() {
        return RdiSerializer.GSON.toJson(this);
    }
}
