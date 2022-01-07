package calebzhou.rdicloudrest.model.record;

import java.io.Serializable;

public class UuidNameRecord implements Serializable {
    String pid;
    String pname;

    public String getPlayerUuid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public UuidNameRecord() {

    }

    public UuidNameRecord(String pid, String pname) {
        this.pid = pid;
        this.pname = pname;
    }
}
