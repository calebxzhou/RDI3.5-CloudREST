package calebzhou.rdicloudrest.model;


import java.io.Serializable;
import java.sql.Timestamp;

public class PlayerAttackRecord implements Serializable {
    String playerUuid;
    String damageSource;
    float hpDrain;
    Timestamp atkTime;


}
