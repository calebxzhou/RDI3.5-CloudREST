package calebzhou.rdicloudrest.module.blockrec;

import calebzhou.rdicloudrest.model.BlockRecord;
import calebzhou.rdicloudrest.utils.FileUtil;
import calebzhou.rdicloudrest.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/BlockRecord")
@Slf4j
public class BlockRecordController {

    @RequestMapping(value = "",method = RequestMethod.POST)
    public void insertRecord(@RequestParam(value = "obj")String obj){
        BlockRecord blockRecord = JsonUtils.getGson().fromJson(obj, BlockRecord.class);
        String location = blockRecord.getLocation();
        File recFile = new File(FileUtil.blockRecFolder,location+".txt");
        FileUtil.writeLineToFile(recFile,blockRecord);
    }
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getRecord(@RequestParam(value = "loca")String loca){
        File recFile = new File(FileUtil.blockRecFolder,loca+".txt");
        if(!recFile.exists())
            return "404：此位置没有方块记录。";
        return FileUtil.readAllLines(recFile);
    }
}
