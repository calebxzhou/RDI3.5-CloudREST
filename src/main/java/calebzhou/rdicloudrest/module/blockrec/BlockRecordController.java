package calebzhou.rdicloudrest.module.blockrec;

import calebzhou.rdicloudrest.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BlockRecord")
@Slf4j
public class BlockRecordController {
    @Autowired
    BlockRecordRepo repo;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void insertRecord(@RequestParam(value = "obj")String obj){
        BlockRecord blockRecord = JsonUtils.getGson().fromJson(obj, BlockRecord.class);
        log.info(blockRecord.location);
        repo.save(blockRecord);
    }
}
