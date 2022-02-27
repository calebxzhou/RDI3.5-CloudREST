package calebzhou.rdicloudrest.module.cloudrepo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CloudRepo extends MongoRepository<ModelCloudRepo,String> {
    @Query("{pid: '?0'}")
    ModelCloudRepo getByPid(String pid);

    @DeleteQuery("{pid: '?0'}")
    void deleteByPid(String pid);
}
