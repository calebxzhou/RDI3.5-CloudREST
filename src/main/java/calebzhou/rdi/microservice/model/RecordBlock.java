package calebzhou.rdi.microservice.model;

import calebzhou.rdi.microservice.utils.RdiSerializer;

import java.io.Serializable;
import java.sql.Timestamp;

public class RecordBlock implements Serializable {
    public String id;
    public String pid;
    public int act;
    public String bid;
    public String world;
    public int x;
    public int y;
    public int z;
    public Timestamp ts;

    public RecordBlock() {
    }

    public RecordBlock(String pid,  String bid,int act, String world, int x, int y, int z, Timestamp ts) {
        this.pid = pid;
        this.act = act;
        this.bid = bid;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ts = ts;
    }

    @Override
    public String toString() {
        return RdiSerializer.GSON.toJson(this);
    }
}
