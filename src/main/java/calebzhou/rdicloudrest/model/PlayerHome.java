package calebzhou.rdicloudrest.model;

import com.google.gson.Gson;
import java.io.Serial;
import java.io.Serializable;

public class PlayerHome implements Serializable {
    //@Serial private static final long serialVersionUID = 1L;
    private String playerUuid;
    private String homeName;
    private String dimension;

    private Double posX,posY,posZ;
    private Float yaw,pitch;
    private String comments;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
