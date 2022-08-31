package calebzhou.rdi.microservice.model.record;

import com.google.gson.Gson;

import java.io.Serializable;
import java.sql.Timestamp;

public class GenericRecord implements Serializable {
    String pid;
    String recordType;
    String src;
    String target;
    String content;
    Timestamp recTime;

    public RecordType getRecordType() {
        try {
            return RecordType.valueOf(recordType);
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getRecTime() {
        return recTime;
    }

    public void setRecTime(Timestamp recTime) {
        this.recTime = recTime;
    }

    public GenericRecord(String pid, RecordType recordType, String src, String target, String content, Timestamp recTime) {
        this.pid = pid;
        this.recordType = recordType.toString();
        this.src = src;
        this.target = target;
        this.content = content;
        this.recTime = recTime;
    }
    public String toString(){
        return new Gson().toJson(this);
    }

}