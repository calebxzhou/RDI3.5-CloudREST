package calebzhou.rdicloudrest.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommandRecord implements Serializable {
    String playerUuid;
    String command;
    Timestamp oprTime;
    public String toString(){
        return new Gson().toJson(this);
    }
}
