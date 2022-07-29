package calebzhou.rdicloudrest.ctrler;

import calebzhou.rdicloudrest.dao.IslandRepo;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.RandomUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Transactional
@RestController
@RequestMapping("/island")
public class IslandCtrler {

    final IslandRepo repo;

    public IslandCtrler(IslandRepo repo) {
        this.repo = repo;
    }
    public boolean isPlayerOwnIsland(String pid){
        Integer iid = repo.findIslandIdOwnByPid(pid);
        return iid!=0;
    }
    public boolean isPlayerJoinIsland(String pid){
        Integer iid = repo.findIslandIdJoinByPid(pid);
        return iid!=0;
    }
    //提供玩家pid获取空岛坐标x,y,z
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public String getIsland(@PathVariable String pid){
        Integer iid = repo.findIslandIdOwnByPid(pid);
        if(iid==0)
            iid=repo.findIslandIdJoinByPid(pid);
        if(iid==0)
            return "fail";
        Island is = repo.findByIid(iid).get();
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
        int iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能删除
        if(iid!=0){
            repo.deleteByIid(iid);
            return 1;
        }
        return 0;
    }

    //提供玩家pid修改空岛坐标，参数x,y,z坐标
    @RequestMapping(value = "/{pid}",method = RequestMethod.PUT)
    public int changeLocation(@PathVariable String pid, @RequestParam String xyz){
        int iid = repo.findIslandIdOwnByPid(pid);
        //必须岛主才能改变坐标
        Optional<Island> isl = repo.findByIid(iid);
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
        int iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能添加成员
        if(iid==0)
            return 0;
        //成员不能重复添加
        Island is = repo.findByIid(iid).get();
        if(is.isMemberExists(mpid))
            return 0;
        is.addMember(mpid);
        repo.save(is);
        return 1;
    }
    //删除空岛成员，提供岛主pid和成员pid
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.DELETE)
    public int removeMember(@PathVariable String pid,@PathVariable String mpid){
        int iid = repo.findIslandIdOwnByPid(pid);
        //必须拥有空岛才能删除成员
        if(iid==0)
            return 0;
        //成员不能重复删除
        Island is = repo.findByIid(iid).get();
        if(!is.isMemberExists(mpid))
            return 0;
        is.removeMember(mpid);
        repo.save(is);
        return 1;
    }









}
