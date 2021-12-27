package calebzhou.rdicloudrest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChatRecord implements Serializable {
    String playerUuid;
    String content;
    Timestamp oprTime;
}
