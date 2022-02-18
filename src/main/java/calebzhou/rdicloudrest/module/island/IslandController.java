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
    @RequestMapping(value = "/island_crew/{pid}/{mpid}",method = RequestMethod.POST)
    public SuccessResponse<Void> addMember(@PathVariable(value = "pid") String pid,@PathVariable("mpid")String mpid){
        service.addMember(pid, mpid);
        return new SuccessResponse<>(null,"您成功添加了成员。");
    }
    @RequestMapping(value = "/island_crew/{pid}/{mpid}",method = RequestMethod.DELETE)
    public SuccessResponse<Void> removeMember(@PathVariable(value = "pid") String pid,@PathVariable("mpid")String mpid){
        service.removeMember(pid, mpid);
        return new SuccessResponse<>(null,"您成功删除了成员。");
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
    public SuccessResponse<Void> delete(@PathVariable(value = "pid") String pid){
        service.delete(pid);
        return new SuccessResponse<Void>(null,"删除成功");
    }


    @RequestMapping(value = "/island/{pid}",method = RequestMethod.PUT)
    public SuccessResponse<Island> changeLocation(@PathVariable(value = "pid") String pid,@RequestParam Optional<String> location){
        return new SuccessResponse<>(service.updateLocation(pid, CoordLocation.fromString(location.get())),"成功更改了空岛坐标。");
    }

}
