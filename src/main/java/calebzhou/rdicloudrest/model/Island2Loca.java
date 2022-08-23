package calebzhou.rdicloudrest.model;

public class Island2Loca {
    int id,iid;
    double x,y,z,w,p;

    public Island2Loca(int id, int iid, double x, double y, double z, double w, double p) {
        this.id = id;
        this.iid = iid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.p = p;
    }

    @Override
    public String toString() {
        return "%.12f,%.12f,%.12f,%.12f,%.12f".formatted(x,y,z,w,p);
    }
}
