package calebzhou.rdicloudrest.module.blockrec;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.sql.Timestamp;

@Document("br")
public class BlockRecord {
    @Id
    public String id;
    String pid;
    String blockType;
    String blockAction;
    String location;
    Timestamp recTime;

    public BlockRecord(String pid, String blockType, String blockAction, String location, Timestamp recTime) {
        this.pid = pid;
        this.blockType = blockType;
        this.blockAction = blockAction;
        this.location = location;
        this.recTime = recTime;
    }

    public BlockRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
