package calebzhou.rdicloudrest.module.cloudrepo;

import calebzhou.rdicloudrest.module.wrapper.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CloudRepoController {
    @Autowired CloudRepo repo;

    @RequestMapping(value = "/island/{pid}",method = RequestMethod.POST)
    public SuccessResponse<Void> saveRepo(@PathVariable(value = "pid") String pid, @RequestParam(value = "obj")String obj){
        repo.save(new ModelCloudRepo(pid,obj));
        return new SuccessResponse<>(null,"保存成功。");
    }
    @RequestMapping(value = "/island/{pid}",method = RequestMethod.GET)
    public SuccessResponse<String> getRepo(@PathVariable(value = "pid") String pid){
        return new SuccessResponse<>(repo.getByPid(pid).getStacks(),"读取成功。");
    }
    @RequestMapping(value = "/island/{pid}",method = RequestMethod.DELETE)
    public SuccessResponse<Void> deleteRepo(@PathVariable(value = "pid") String pid, @RequestParam(value = "obj")String obj){
        repo.save(new ModelCloudRepo(pid,obj));
        return new SuccessResponse<>(null,"删除成功。");
    }
}
