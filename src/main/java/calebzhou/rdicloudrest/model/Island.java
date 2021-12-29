package calebzhou.rdicloudrest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Island implements Serializable {
    String islandId;
    String ownerUuid;
    String location;
    Timestamp createTime;
}
