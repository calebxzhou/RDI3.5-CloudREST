package calebzhou.rdicloudrest.ctrler;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
public class RecordCtrler {
    public void recordChat(){

    }
    public void recordDeath(){

    }
    @RequestMapping(value = "/idname/{pid}/{name}",method = RequestMethod.POST)
    public void recordIdName(@PathVariable String pid, @PathVariable String name){

    }
    @RequestMapping(value = "/hwinfo/{pid}",method = RequestMethod.POST)
    public void recordHardwareInfo(@PathVariable String pid){

    }

}
