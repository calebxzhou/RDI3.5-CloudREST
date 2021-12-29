package calebzhou.rdicloudrest.model;

import com.google.gson.Gson;

import java.io.Serializable;

//坐标位置，XYZ
public class CoordLocation implements Serializable {
    int posX,posY,posZ;

    public CoordLocation(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    public static CoordLocation fromString(String json){
        return new Gson().fromJson(json,CoordLocation.class);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }
}
