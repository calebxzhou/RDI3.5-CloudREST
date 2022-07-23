package calebzhou.rdicloudrest.module.qqbind;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class QBind {
    @Id @Column
    String id;
    @Column
    String qq;

    public QBind() {
    }

    public String getId() {
        return id;
    }

    public void setId(String qq) {
        this.id = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String pid) {
        this.qq = pid;
    }

    public QBind(String id, String qq) {
        this.id = id;
        this.qq = qq;
    }
}
