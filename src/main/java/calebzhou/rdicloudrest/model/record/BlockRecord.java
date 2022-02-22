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

    public BlockRecord() {
    }

    public BlockRecord(String pid, String blockType, String blockAction, String location) {
        this.pid = pid;
        this.blockType = blockType;
        this.blockAction = blockAction;
        this.location = location;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getBlockAction() {
        return blockAction;
    }

    public void setBlockAction(String blockAction) {
        this.blockAction = blockAction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getRecTime() {
        return recTime;
    }

    public void setRecTime(Timestamp recTime) {
        this.recTime = recTime;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}