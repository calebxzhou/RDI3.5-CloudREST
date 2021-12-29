package calebzhou.rdicloudrest.model.record;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogInOutRecord implements Serializable {
    String playerUuid;
    String logAction;//IN OUT
    String ipAddr;//IN OUT
    Timestamp oprTime;
}
