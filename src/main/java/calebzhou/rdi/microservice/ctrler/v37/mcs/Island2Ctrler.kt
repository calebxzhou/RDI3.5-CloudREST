package calebzhou.rdi.microservice.ctrler.v37.mcs

import calebzhou.rdi.microservice.annotation.PidTokenCheck
import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.dao.IslandMapper
import calebzhou.rdi.microservice.model.Island2
import calebzhou.rdi.microservice.utils.TimeUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Created by calebzhou on 2022-10-05,7:58.
 */
@RestController
@RequestMapping("/v37/mcs_game/island")
class Island2Ctrler(var mapper: IslandMapper) {
    //获取岛屿基本信息 成功返回岛屿数据
    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.GET])
    fun getIsland( @PathVariable pid: String): Any? {
        val iid= mapper.getPlayerOwnIslandId(pid)?: mapper.getPlayerJoinIslandId(pid)?:return ResponseCode.SourceNotJoinAnyIsland
        return mapper.getIslandById(iid)
    }
    //提供玩家pid创建新的空岛，成功返回岛屿id
    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.POST])
    fun createIsland(@PathVariable pid: String): Any? {
        if (mapper.isPlayerOwnIsland(pid)) {
            return ResponseCode.SourceAlreadyOwnIsland
        }
        if (mapper.isPlayerJoinAnyIsland(pid)) {
            return ResponseCode.SourceAlreadyJoinAnyIsland
        }
        val island2 = Island2()
        island2.pid = pid
        island2.ts = TimeUtils.getNow()
        mapper.insertIsland(island2)
        return mapper.getPlayerOwnIslandId(pid)
    }

    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.DELETE])
    fun deleteIsland(@PathVariable pid: String, request: HttpServletRequest
    ): Any? {
        //没岛不删
        val iid = mapper.getPlayerOwnIslandId(pid) ?: return ResponseCode.SourceNotOwnIsland
        mapper.deleteIsland(iid)
        return iid
    }

    //提供玩家pid修改岛主
    @PidTokenCheck
    @RequestMapping(value = ["/transfer/{pid}/{targetPid}"], method = [RequestMethod.PUT])
    fun transferIsland(@PathVariable pid: String, @PathVariable targetPid: String): Any? {
        //不能转给自己,自己必须有岛，自己不能加岛，对方不能有岛，对方不能加岛，
        if (pid == targetPid) {
            return ResponseCode.SourceEqualsTarget
        }
        if (mapper.isPlayerJoinAnyIsland(pid)) {
            return ResponseCode.SourceAlreadyJoinAnyIsland
        }
        if (mapper.isPlayerOwnIsland(targetPid)) {
            return ResponseCode.TargetAlreadyOwnIsland
        }
        if (mapper.isPlayerJoinAnyIsland(targetPid)) {
            return ResponseCode.TargetAlreadyJoinAnyIsland
        }
        val iid = mapper.getPlayerOwnIslandId(pid)?:return ResponseCode.SourceNotOwnIsland
        mapper.updateIslandPid(iid, targetPid)
        return ResponseCode.Success
    }


    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    @PidTokenCheck
    @RequestMapping(value = ["/loca/{pid}"], method = [RequestMethod.PUT])
    fun changeLocation(@PathVariable pid: String,
                       @RequestParam x: Double,
                       @RequestParam y: Double,
                       @RequestParam z: Double,
                       @RequestParam w: Double,
                       @RequestParam p: Double): ResponseCode {
        //没有岛屿改不了
        val iid = mapper.getPlayerOwnIslandId(pid)?:return ResponseCode.SourceNotOwnIsland
        mapper.updateIslandLocation(iid, x, y, z, w, p)
        return ResponseCode.Success
    }

    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    @RequestMapping(value = ["/crew/{pid}/{mpid}"], method = [RequestMethod.POST])
    fun addMember(@PathVariable pid: String, @PathVariable mpid: String): ResponseCode {
        //不能添加自己
        if (pid == mpid)
            return ResponseCode.SourceEqualsTarget
        //对方已经有岛了，不加
        if (mapper.isPlayerOwnIsland(mpid)) {
            return ResponseCode.TargetAlreadyOwnIsland
        }
        //对方已经进岛了，不加
        if (mapper.isPlayerJoinAnyIsland(mpid)) {
            return ResponseCode.TargetAlreadyJoinAnyIsland
        }
        val iid = mapper.getPlayerOwnIslandId(pid)?:return ResponseCode.SourceNotOwnIsland
        mapper.insertMember(mpid, iid)
        return ResponseCode.Success
    }

    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    @RequestMapping(value = ["/crew/{mpid}"], method = [RequestMethod.DELETE])
    fun removeMember(@PathVariable mpid: String): ResponseCode {
        //目标有自己的岛，不删
        if (mapper.isPlayerOwnIsland(mpid)) return ResponseCode.TargetAlreadyOwnIsland
        //目标没进任何岛 不删
        val iid = mapper.getPlayerJoinIslandId(mpid)?:return ResponseCode.TargetNotJoinAnyIsland
        mapper.deleteMember(mpid, iid)
        return ResponseCode.Success
    }
}