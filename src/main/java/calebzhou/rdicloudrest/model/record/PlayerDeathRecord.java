package calebzhou.rdicloudrest.model.record;


import java.io.Serializable;
import java.sql.Timestamp;

public class PlayerDeathRecord implements Serializable {
    String playerUuid;
    String damageSource;
    Timestamp deathTime;

}
