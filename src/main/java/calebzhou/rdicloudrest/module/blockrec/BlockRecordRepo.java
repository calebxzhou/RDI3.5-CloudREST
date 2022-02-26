package calebzhou.rdicloudrest.module.blockrec;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRecordRepo extends MongoRepository<BlockRecord,String> {
}
