package calebzhou.rdicloudrest.module.cloudrepo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("cloudRepo")
public class ModelCloudRepo {
    @Id
    public String id;
    String pid;
    String stacks;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getStacks() {
        return stacks;
    }

    public void setStacks(String stacks) {
        this.stacks = stacks;
    }

    public ModelCloudRepo(String pid, String stacks) {
        this.pid = pid;
        this.stacks = stacks;
    }

}