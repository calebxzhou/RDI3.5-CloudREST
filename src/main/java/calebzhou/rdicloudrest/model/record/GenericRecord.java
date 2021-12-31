package calebzhou.rdicloudrest.model.record;

import java.io.Serializable;
import java.sql.Timestamp;

public class GenericRecord implements Serializable {
    String pid;
    String recordType;
    String src;
    String target;
    String content;
    Timestamp recTime;

    public GenericRecord(String pid, RecordType recordType, String src, String target, String content, Timestamp recTime) {
        this.pid = pid;
        this.recordType = recordType.toString();
        this.src = src;
        this.target = target;
        this.content = content;
        this.recTime = recTime;
    }
}