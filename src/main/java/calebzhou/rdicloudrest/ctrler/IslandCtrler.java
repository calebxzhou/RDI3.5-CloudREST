package calebzhou.rdicloudrest.ctrler;

import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.model.IslandCrew;
import calebzhou.rdicloudrest.dao.IslandRepo;
import calebzhou.rdicloudrest.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/island")
public class IslandCtrler {

    final IslandRepo repo;

    public IslandCtrler(IslandRepo repo) {
        this.repo = repo;
    }
    public boolean isPlayerOwnIsland(String pid){
        String iid = repo.findIslandIdOwnByPid(pid);
        return !StringUtils.isEmpty(iid);
    }
    public boolean isPlayerJoinIsland(String pid){
        String iid = repo.findIslandIdJoinByPid(pid);
        return !StringUtils.isEmpty(iid);
    }
    //提供玩家pid获取空岛坐标x,y,z
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public String getIsland(@PathVariable String pid){
        String iid = repo.findIslandIdOwnByPid(pid);
        if(StringUtils.isEmpty(iid))
            iid=repo.findIslandIdJoinByPid(pid);
        if(StringUtils.isEmpty(iid))
            return "fail";
        Island is = repo.findById(iid).get();
        return is.getLocation();
    }
    //提供玩家pid创建新的空岛，返回x,y,z格式位置
    @RequestMapping(value = "/{pid}",method = RequestMethod.POST)
    public String createIsland(@PathVariable String pid){
        if(isPlayerOwnIsland(pid) || isPlayerJoinIsland(pid)){
            return "fail";
        }
        Island is = new Island();
        is.setPid(pid);
        int x = RandomUtils.generateRandomInt(-49999, 49999);
        int y = 128;
        int z = RandomUtils.generateRandomInt(-49999, 49999);
        is.setX(x);
        is.setY(y);
        is.setZ(z);
        repo.save(is);
        return is.getLocation();
    }
    //提供玩家pid删除空岛
    @RequestMapping(value = "/{pid}",method = RequestMethod.DELETE)
    public int deleteIsland(@PathVariable String pid){
        String iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能删除
        if(!StringUtils.isEmpty(iid)){
            repo.deleteById(iid);
            return 1;
        }
        return 0;
    }

    //提供玩家pid修改空岛坐标，参数x,y,z坐标
    @RequestMapping(value = "/{pid}",method = RequestMethod.PUT)
    public int changeLocation(@PathVariable String pid, @RequestParam String xyz){
        String iid = repo.findIslandIdOwnByPid(pid);
        //必须岛主才能改变坐标
        Optional<Island> isl = repo.findById(iid);
        if(isl.isEmpty()){
            return 0;
        }
        Island is = isl.get();
        is.setLocation(xyz);

        return 1;
    }

    //添加空岛成员，提供岛主pid和成员pid
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.POST)
    public int addMember(@PathVariable String pid,@PathVariable String mpid){
        String iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能添加成员
        if(StringUtils.isEmpty(iid))
            return 0;
        //成员不能重复添加
        Island is = repo.findById(iid).get();
        if(is.isMemberExists(mpid))
            return 0;
        is.addMember(new IslandCrew(is,mpid));
        repo.save(is);
        return 1;
    }
    //删除空岛成员，提供岛主pid和成员pid
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.DELETE)
    public int removeMember(@PathVariable String pid,@PathVariable String mpid){
        String iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能删除成员
        if(StringUtils.isEmpty(iid))
            return 0;
        //成员不能重复删除
        Island is = repo.findById(iid).get();
        if(!is.isMemberExists(mpid))
            return 0;
        is.removeMember(new IslandCrew(is,mpid));
        repo.save(is);
        return 1;
    }









}
