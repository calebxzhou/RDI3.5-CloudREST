package calebzhou.rdi.microservice.model;


import calebzhou.rdi.microservice.utils.RdiSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Island2Loca implements  Serializable{
    transient int id;
    transient int iid;
    double x;
    double y;
    double z;
    double w;
    double p;

    @Override
    public String toString() {
        return RdiSerializer.GSON.toJson(this);
    }
}
