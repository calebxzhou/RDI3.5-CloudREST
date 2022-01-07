package calebzhou.rdicloudrest.model.record;

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