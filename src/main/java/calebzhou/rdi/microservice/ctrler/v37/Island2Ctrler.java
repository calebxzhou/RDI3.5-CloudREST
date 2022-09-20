package calebzhou.rdi.microservice.ctrler.v37;

import calebzhou.rdi.microservice.annotation.PidTokenCheck;
import calebzhou.rdi.microservice.constant.ResultCode;
import calebzhou.rdi.microservice.dao.Island2Mapper;
import calebzhou.rdi.microservice.utils.JwtUtils;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import calebzhou.rdi.microservice.model.Island2;
import calebzhou.rdi.microservice.utils.TimeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v37/island2")
public class Island2Ctrler {
    final Island2Mapper mapper;
    public Island2Ctrler(Island2Mapper mapper) {
        this.mapper = mapper;
    }

    //获取岛屿基本信息 成功返回岛屿数据
    @PidTokenCheck
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public Object getIsland(/*@RequestHeader("rauth") String token,*/@PathVariable String pid){
        boolean playerOwnIsland = mapper.isPlayerOwnIsland(pid);
        boolean playerJoinAnyIsland = mapper.isPlayerJoinAnyIsland(pid);
        if(!playerOwnIsland && !playerJoinAnyIsland){
            return ResultCode.sourceNotJoinAnyIsland;
        }
        Integer iid;
        if(playerOwnIsland){
            iid = mapper.getPlayerOwnIslandId(pid);
        }else if(playerJoinAnyIsland){
            iid = mapper.getPlayerJoinIslandId(pid);
        }else{
            return ResultCode.sourceNotJoinAnyIsland;
        }
        Island2 island = mapper.getIslandById(iid);
        return island;
    }

    //提供玩家pid创建新的空岛，成功返回岛屿id
    @PidTokenCheck
    @RequestMapping(value = "/{pid}",method = RequestMethod.POST)
    public Object createIsland(/*@RequestHeader("rauth") String token,*/@PathVariable String pid){
        if(mapper.isPlayerOwnIsland(pid)){
            return ResultCode.sourceAlreadyOwnIsland;
        }
        if(mapper.isPlayerJoinAnyIsland(pid)){
            return ResultCode.sourceAlreadyJoinAnyIsland;
        }
        Island2 island2 = new Island2();
        island2.pid = pid;
        island2.ts = TimeUtils.getNow();
        mapper.insertIsland(island2);
        return mapper.getPlayerOwnIslandId(pid);
    }
    //提供玩家pid删除空岛，返回删除的岛屿id
    @PidTokenCheck
    @RequestMapping(value = "/{pid}",method = RequestMethod.DELETE)
    public Object sxstdeleteIsland(/*@RequestHeader("rauth") String token,*/@PathVariable String pid){
        //没岛不删
        if(!mapper.isPlayerOwnIsland(pid)){
            return ResultCode.sourceNotOwnIsland;
        }
        Integer iid = mapper.getPlayerOwnIslandId(pid);
        if(iid == null)
            return ResultCode.sourceNotOwnIsland;
        mapper.deleteIsland(iid);
        return iid;
    }
    //提供玩家pid修改岛主
    @PidTokenCheck
    @RequestMapping(value = "/transfer/{pid}/{targetPid}",method = RequestMethod.PUT)
    public ResultCode transferIsland(/*@RequestHeader("rauth") String token,*/@PathVariable String pid,@PathVariable String targetPid){
        //不能转给自己,自己必须有岛，自己不能加岛，对方不能有岛，对方不能加岛，
        if(pid.equals(targetPid)){
            return ResultCode.sourceEqualsTarget;
        }
        if(!mapper.isPlayerOwnIsland(pid)){
            return ResultCode.sourceNotOwnIsland;
        }
        if(mapper.isPlayerJoinAnyIsland(pid)){
            return ResultCode.sourceAlreadyJoinAnyIsland;
        }
        if(mapper.isPlayerOwnIsland(targetPid)){
            return ResultCode.targetAlreadyOwnIsland;
        }
        if(mapper.isPlayerJoinAnyIsland(targetPid)){
            return ResultCode.targetAlreadyJoinAnyIsland;
        }
        Integer iid = mapper.getPlayerOwnIslandId(pid);
        mapper.updateIslandPid(iid,targetPid);
        return ResultCode.success;
    }

    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    @PidTokenCheck
    @RequestMapping(value = "/loca/{pid}",method = RequestMethod.PUT)
    public ResultCode changeLocation(/*@RequestHeader("rauth") String token,*/@PathVariable String pid,@RequestParam String dim, @RequestParam double x,@RequestParam double y,@RequestParam double z,
                              @RequestParam double w,@RequestParam double p){
        //没有岛屿改不了
        if(!mapper.isPlayerOwnIsland(pid)){
            return ResultCode.sourceNotOwnIsland;
        }
        int iid = mapper.getPlayerOwnIslandId(pid);
        mapper.updateIslandLocation(iid,x,y,z,w,p);
        return ResultCode.success;
    }

    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    @PidTokenCheck
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.POST)
    public ResultCode addMember(/*@RequestHeader("rauth") String token,*/@PathVariable String pid,@PathVariable String mpid){
        //没岛不加
        if(!mapper.isPlayerOwnIsland(pid))
            return ResultCode.sourceNotOwnIsland;
        //不能添加自己
        if(pid.equals(mpid))
            return ResultCode.sourceEqualsTarget;
        //对方已经有岛了，不加
        if (mapper.isPlayerOwnIsland(mpid)) {
            return ResultCode.targetAlreadyOwnIsland;
        }
        //对方已经进岛了，不加
        if(mapper.isPlayerJoinAnyIsland(mpid)){
            return ResultCode.targetAlreadyJoinAnyIsland;
        }
        int iid = mapper.getPlayerOwnIslandId(pid);
        mapper.insertMember(mpid,iid);
        return ResultCode.success;
    }
    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    @PidTokenCheck
    @RequestMapping(value = "/crew/{mpid}",method = RequestMethod.DELETE)
    public ResultCode removeMember(@RequestHeader("rauth") String token/*,@PathVariable String pid*/,@PathVariable String mpid){
        //自己没有岛 不删
        /*if(!mapper.isPlayerOwnIsland(pid))
            return ResultCode.sourceNotOwnIsland;*/
        //不能删除自己
        /*if(pid.equals(mpid))
            return ResultCode.sourceEqualsTarget;*/
        //目标有自己的岛，不删
        if(mapper.isPlayerOwnIsland(mpid))
            return ResultCode.targetAlreadyOwnIsland;
        //目标没进任何岛 不删
        if(!mapper.isPlayerJoinAnyIsland(mpid))
            return ResultCode.targetNotJoinAnyIsland;
        //Integer iidOwn = mapper.getPlayerJoinIslandId(mpid);
        //目标加入的岛不归自己 不删
        /*if(mapper.isPlayerJoinAnyIsland(mpid) && !mapper.isPlayerJoinOneIsland(mpid,iidOwn))
            return ResultCode.sourceNotOwnTarget;*/
        Integer iid = mapper.getPlayerJoinIslandId(mpid);
        mapper.deleteMember(mpid,iid);
        return ResultCode.success;
    }
}
