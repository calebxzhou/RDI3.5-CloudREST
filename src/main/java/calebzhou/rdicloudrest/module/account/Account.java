package calebzhou.rdicloudrest.module.account;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public final class Account {
    @Id
    @Column
    private long qq;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    public Account() {
    }

    public Account(long qq, Date regDate) {
        this.qq = qq;
        this.regDate = regDate;
    }

    public long getQq() {
        return qq;
    }

    public void setQq(long qq) {
        this.qq = qq;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
