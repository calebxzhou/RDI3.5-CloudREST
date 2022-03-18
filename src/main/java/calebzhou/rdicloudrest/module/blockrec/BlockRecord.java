package calebzhou.rdicloudrest.module.blockrec;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class BlockRecord {
    @Id
    private String id;
    private String pid;
    private String blockType;
    private  String blockAction;
    private  String location;
    private  Timestamp recTime;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("pid", pid)
                .append("blockType", blockType)
                .append("blockAction", blockAction)
                .append("location", location)
                .append("recTime", recTime)
                .toString();
    }
}
