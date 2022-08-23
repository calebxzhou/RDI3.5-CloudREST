package calebzhou.rdicloudrest.model;

import calebzhou.rdicloudrest.utils.TimeUtils;

import java.sql.Timestamp;

public class Island2 {
    int iid;
    String pid;
    Timestamp ts;
    Island2Loca loca;

    public Island2(int iid, String pid) {
        this.iid = iid;
        this.pid = pid;
        this.ts= TimeUtils.getNow();
    }

    public Island2(int iid, String pid, Timestamp ts, Island2Loca loca) {
        this.iid = iid;
        this.pid = pid;
        this.ts = ts;
        this.loca=loca;
    }

    @Override
    public String toString() {
        return "%d,%s".formatted(iid,loca.toString());
    }
}
