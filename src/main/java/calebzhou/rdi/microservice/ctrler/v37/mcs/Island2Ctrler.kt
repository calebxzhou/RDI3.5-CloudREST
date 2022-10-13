package calebzhou.rdi.microservice.ctrler.v37.mcs

import calebzhou.rdi.microservice.annotation.PidTokenCheck
import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.dao.IslandMapper
import calebzhou.rdi.microservice.model.entity.Island2
import calebzhou.rdi.microservice.service.IslandService
import calebzhou.rdi.microservice.utils.TimeUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Created by calebzhou on 2022-10-05,7:58.
 */
@RestController
@RequestMapping("/v37/mcs_game/island2")
class Island2Ctrler(var service: IslandService) {
    //获取岛屿基本信息 成功返回岛屿数据
    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.GET])
    fun getIsland( @PathVariable pid: String): Any? {
        return service.getIsland2Vo(pid)
    }
    //提供玩家pid创建新的空岛，成功返回岛屿id
    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.POST])
    fun createIsland(@PathVariable pid: String): Any? {
        return service.createIsland2(pid)
    }

    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.DELETE])
    fun deleteIsland(@PathVariable pid: String ): Any? {
        return service.deleteIsland2(pid)
    }

    //提供玩家pid修改岛主
    @PidTokenCheck
    @RequestMapping(value = ["/transfer/{pid}/{targetPid}"], method = [RequestMethod.PUT])
    fun transferIsland(@PathVariable pid: String, @PathVariable targetPid: String): Any? {
        return service.transferIsland(pid, targetPid)
    }
    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    @PidTokenCheck
    @RequestMapping(value = ["/loca/{pid}"], method = [RequestMethod.PUT])
    fun changeLocation(@PathVariable pid: String,
                       @RequestParam x: Double,
                       @RequestParam y: Double,
                       @RequestParam z: Double,
                       @RequestParam w: Double,
                       @RequestParam p: Double){
        return service.changeLocation(pid, x, y, z, w, p)
    }

    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    @RequestMapping(value = ["/crew/{pid}/{mpid}"], method = [RequestMethod.POST])
    fun addMember(@PathVariable pid: String, @PathVariable mpid: String) {
        return service.addMember(pid, mpid)
    }

    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    @RequestMapping(value = ["/crew/{mpid}"], method = [RequestMethod.DELETE])
    fun removeMember(@PathVariable mpid: String) {
        return service.removeMember(mpid)
    }
}