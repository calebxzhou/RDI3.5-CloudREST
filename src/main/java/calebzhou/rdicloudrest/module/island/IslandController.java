package calebzhou.rdicloudrest.module.island;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.module.wrapper.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2")
public class IslandController {
    @Autowired
    IslandService service;

    @RequestMapping(value = "/island/{pid}",method = RequestMethod.POST)
    public SuccessResponse<Island> createIsland(@PathVariable(value = "pid") String pid){
        return new SuccessResponse<>(service.create(pid),"您成功创建了空岛。");
    }

    @RequestMapping(value = "/island",method = RequestMethod.GET)
    public List<Island> getAll(){
        return service.getAll();
    }

    @RequestMapping(value = "/island/{pid}",method = RequestMethod.GET)
    public Optional<Island> getByPid(@PathVariable(value = "pid") String pid){
        return service.getById(pid);
    }

    @RequestMapping(value = "/island/{pid}",method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "pid") String pid){
        service.delete(pid);
    }

    @RequestMapping(value = "/island/{pid}",method = RequestMethod.PUT)
    public SuccessResponse<Island> changeLocation(@PathVariable(value = "pid") String pid,@RequestParam Optional<String> location){
        return new SuccessResponse<>(service.updateLocation(pid, CoordLocation.fromString(location.get())),"成功更改了空岛坐标。");
    }

}
