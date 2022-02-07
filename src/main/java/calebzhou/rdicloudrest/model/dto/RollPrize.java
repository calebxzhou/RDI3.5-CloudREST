package calebzhou.rdicloudrest.model.dto;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;

public class RollPrize implements Serializable {
    public enum Type{
        item,
        creature,
        exp
    }
    String id;
    String type;
    String descr;
    //概率 0~1
    float proba;
    int count;

    public RollPrize() {
    }

    public RollPrize(String id, Type type, String descr, float proba, int count) {
        this.id = id;
        this.type = type.toString();
        this.descr = descr;
        this.proba = proba;
        this.count = count;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    //成功抽到奖
    public boolean getPrizeSuccessful(){
        return RandomUtils.nextInt(0,10)< getProba()*10;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return Type.valueOf(type);
    }

    public void setType(Type type) {
        this.type = type.toString();
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public float getProba() {
        return proba;
    }

    public void setProba(float proba) {
        this.proba = proba;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
