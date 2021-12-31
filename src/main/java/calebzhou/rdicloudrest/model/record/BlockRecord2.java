package calebzhou.rdicloudrest.model.record;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlockRecord2 implements Serializable {
    String pid;
    String blockType;
    String blockAction;
    String location;
    Timestamp recTime;
}