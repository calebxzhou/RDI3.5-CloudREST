package calebzhou.rdicloudrest.module.qqbind;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class QBind {
    @Id @Column
    String qq;
    @Column
    String pid;

    public QBind() {
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public QBind(String qq, String pid) {
        this.qq = qq;
        this.pid = pid;
    }
}
