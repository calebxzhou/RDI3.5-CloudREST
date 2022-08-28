package calebzhou.rdicloudrest.ctrler;

import calebzhou.rdicloudrest.dao.Island2Mapper;
import calebzhou.rdicloudrest.model.Island2;
import calebzhou.rdicloudrest.model.Island2Error;
import calebzhou.rdicloudrest.utils.RdiSerializer;
import calebzhou.rdicloudrest.utils.TimeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/island2")
public class Island2Ctrler {
    final Island2Mapper mapper;
    public Island2Ctrler(Island2Mapper mapper) {
        this.mapper = mapper;
    }

    //获取岛屿基本信息 成功返回岛屿数据
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public String getIsland(@PathVariable String pid){
        boolean playerOwnIsland = mapper.isPlayerOwnIsland(pid);
        boolean playerJoinAnyIsland = mapper.isPlayerJoinAnyIsland(pid);
        if(!playerOwnIsland && !playerJoinAnyIsland){
            return Island2Error.sourceNotJoinAnyIsland+"";
        }
        Integer iid;
        if(playerOwnIsland){
            iid = mapper.getPlayerOwnIslandId(pid);
        }else if(playerJoinAnyIsland){
            iid = mapper.getPlayerJoinIslandId(pid);
        }else{
            return Island2Error.sourceNotJoinAnyIsland+"";
        }
        Island2 island = mapper.getIslandById(iid);
        return RdiSerializer.GSON.toJson(island);
    }

    //提供玩家pid创建新的空岛，成功返回岛屿id
    @RequestMapping(value = "/{pid}",method = RequestMethod.POST)
    public int createIsland(@PathVariable String pid){
        if(mapper.isPlayerOwnIsland(pid)){
            return Island2Error.sourceAlreadyOwnIsland;
        }
        if(mapper.isPlayerJoinAnyIsland(pid)){
            return Island2Error.sourceAlreadyJoinAnyIsland;
        }
        Island2 island2 = new Island2();
        island2.pid = pid;
        island2.ts = TimeUtils.getNow();
        mapper.insertIsland(island2);
        return mapper.getPlayerOwnIslandId(pid);
    }
    //提供玩家pid删除空岛
    @RequestMapping(value = "/{pid}",method = RequestMethod.DELETE)
    public int deleteIsland(@PathVariable String pid){
        //没岛不删
        if(!mapper.isPlayerOwnIsland(pid)){
            return Island2Error.sourceNotOwnIsland;
        }
        Integer playerOwnIslandId = mapper.getPlayerOwnIslandId(pid);
        if(playerOwnIslandId == null)
            return Island2Error.sourceNotOwnIsland;
        return 1;
    }

    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    @RequestMapping(value = "/{pid}",method = RequestMethod.PUT)
    public int changeLocation(@PathVariable String pid, @RequestParam double x,@RequestParam double y,@RequestParam double z,
                              @RequestParam double w,@RequestParam double p){
        //没有岛屿改不了
        if(!mapper.isPlayerOwnIsland(pid)){
            return Island2Error.sourceNotOwnIsland;
        }
        int iid = mapper.getPlayerOwnIslandId(pid);
        mapper.updateIslandLocation(iid,x,y,z,w,p);
        return 1;
    }

    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.POST)
    public int addMember(@PathVariable String pid,@PathVariable String mpid){
        //没岛不加
        if(!mapper.isPlayerOwnIsland(pid))
            return Island2Error.sourceNotOwnIsland;
        //不能添加自己
        if(pid.equals(mpid))
            return Island2Error.sourceEqualsTarget;
        //对方已经有岛了，不加
        if (mapper.isPlayerOwnIsland(mpid)) {
            return Island2Error.targetAlreadyOwnIsland;
        }
        //对方已经进岛了，不加
        if(mapper.isPlayerJoinAnyIsland(mpid)){
            return Island2Error.targetAlreadyJoinAnyIsland;
        }
        int iid = mapper.getPlayerOwnIslandId(pid);
        mapper.insertMember(mpid,iid);
        return 1;
    }
    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    @RequestMapping(value = "/crew/{mpid}",method = RequestMethod.DELETE)
    public int removeMember(@PathVariable String mpid){
        //目标有自己的岛，不删
        if(mapper.isPlayerOwnIsland(mpid))
            return Island2Error.targetAlreadyOwnIsland;
        //目标没进任何岛 不删
        if(!mapper.isPlayerJoinAnyIsland(mpid))
            return Island2Error.targetNotJoinAnyIsland;
        Integer iid = mapper.getPlayerJoinIslandId(mpid);
        mapper.deleteMember(mpid,iid);
        return 1;
    }
}
