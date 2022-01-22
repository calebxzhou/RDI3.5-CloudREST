package calebzhou.rdicloudrest.model.record;

import com.google.gson.Gson;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlockRecord implements Serializable {
    String pid;
    String blockType;
    String blockAction;
    String location;
    Timestamp recTime;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}