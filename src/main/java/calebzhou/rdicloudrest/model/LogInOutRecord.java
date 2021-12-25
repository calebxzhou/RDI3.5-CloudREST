package calebzhou.rdicloudrest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogInOutRecord implements Serializable {
    String playerUuid;
    String inout;//IN OUT
    Timestamp oprTime;
}
