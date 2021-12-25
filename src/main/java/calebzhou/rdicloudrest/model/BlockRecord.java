package calebzhou.rdicloudrest.model;

import calebzhou.rdicloudrest.constants.BlockAction;
import com.google.gson.Gson;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlockRecord implements Serializable {
    String playerUuid;
    String blockType;
    String blockAction;
    String dimension;
    Integer posX;
    Integer posY;
    Integer posZ;
    Timestamp oprTime;
    public String toString(){
        return new Gson().toJson(this);
    }
}
