package calebzhou.rdi.microservice.service

import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.dao.IslandMapper
import calebzhou.rdi.microservice.exception.RdiResponseCodeException
import calebzhou.rdi.microservice.model.entity.Island2
import calebzhou.rdi.microservice.model.vo.Island2Vo
import org.springframework.stereotype.Service

/**
 * Created by calebzhou on 2022-10-07,22:40.
 */

@Service
class IslandService(var mapper: IslandMapper) {

    //获取1岛
    fun getIsland1(pid: String): String {
        return mapper.findIsland1IdOwnByPid(pid) ?:mapper.findIsland1IdJoinByPid(pid)?:"fail"
    }
    //获取岛屿信息
    fun getIsland2(pid:String): Island2 {
        val iid= mapper.getPlayerOwnIslandId(pid)?: mapper.getPlayerJoinIslandId(pid)
        val island2 = mapper.getIslandById(iid) ?: throw RdiResponseCodeException(ResponseCode.SourceNotJoinAnyIsland)
        //TODO convert to VO
        Island2Vo(island2.iid,)
        return island2
    }
}