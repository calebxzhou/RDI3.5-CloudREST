package calebzhou.rdicloudrest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlayerMonitor implements Serializable {
        String playerUuid, dimension;
        float health;
        double x,y,z;
        Timestamp times;

        }