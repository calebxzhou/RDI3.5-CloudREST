package calebzhou.rdicloudrest.web;

public class RdiUser {
    private String usr;
    private String qq;
    private String pwd;
    private String nick;

    public RdiUser() {
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public RdiUser(String usr, String qq, String pwd, String nick) {
        this.usr = usr;
        this.qq = qq;
        this.pwd = pwd;
        this.nick = nick;
    }
}
