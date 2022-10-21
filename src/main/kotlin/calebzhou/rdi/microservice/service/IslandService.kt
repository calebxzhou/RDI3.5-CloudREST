package calebzhou.rdi.microservice.service

import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.dao.IslandMapper
import calebzhou.rdi.microservice.dao.RecordMapper
import calebzhou.rdi.microservice.exception.RdiResponseCodeException
import calebzhou.rdi.microservice.model.entity.Island2
import calebzhou.rdi.microservice.model.entity.RdiPlayer
import calebzhou.rdi.microservice.model.vo.Island2CrewVo
import calebzhou.rdi.microservice.model.vo.Island2Vo
import calebzhou.rdi.microservice.utils.TimeUtils
import okhttp3.internal.toImmutableList
import org.springframework.stereotype.Service

/**
 * Created by calebzhou on 2022-10-07,22:40.
 */

@Service
class IslandService(var mapper: IslandMapper,var records:RecordMapper) {

    //获取1岛
    fun getIsland1(pid: String): String {
        return mapper.findIsland1IdOwnByPid(pid) ?:mapper.findIsland1IdJoinByPid(pid)?:"fail"
    }
    //获取岛屿信息
    fun getIsland2(pid:String):Island2{
        val iid= mapper.getPlayerOwnIslandId(pid)?: mapper.getPlayerJoinIslandId(pid)?:throw RdiResponseCodeException(ResponseCode.SourceNotJoinAnyIsland)
        return mapper.getIslandById(iid) ?: throw RdiResponseCodeException(ResponseCode.SourceNotJoinAnyIsland)
    }
    //获取岛屿信息vo
    fun getIsland2Vo(pid:String): Island2Vo {
        val island2 = getIsland2(pid)
        val islandOwnerRecord = records.selectRecordIdNameByPid(island2.pid)!!
        val islandOwner = RdiPlayer(island2.pid, islandOwnerRecord.pname, islandOwnerRecord.ts)
        val crewList = island2.crews.map { crew ->
            val record = records.selectRecordIdNameByPid(crew.pid)
            Island2CrewVo(RdiPlayer(record!!.pid, record.pname, record.ts),crew.ts)
        }
            .toImmutableList()

        return Island2Vo(island2.iid,islandOwner,island2.ts,island2.loca,crewList)
    }
    //提供玩家pid创建新的空岛，成功返回岛屿id
    fun createIsland2(pid: String): Int {
        if (mapper.isPlayerOwnIsland(pid)) {
            throw RdiResponseCodeException(ResponseCode.SourceAlreadyOwnIsland)
        }
        if (mapper.isPlayerJoinAnyIsland(pid)) {
            throw RdiResponseCodeException( ResponseCode.SourceAlreadyJoinAnyIsland)
        }
        mapper.createIsland(pid, TimeUtils.now)
        return mapper.getPlayerOwnIslandId(pid) ?: throw RdiResponseCodeException(ResponseCode.SourceNotJoinAnyIsland)
    }
    //删除岛屿
    fun deleteIsland2(pid: String ): Int {
        //没岛不删
        val iid = mapper.getPlayerOwnIslandId(pid) ?: throw RdiResponseCodeException(ResponseCode.SourceNotOwnIsland)
        mapper.deleteIsland(iid)
        return iid
    }
    //提供玩家pid修改岛主
    fun transferIsland(pid:String,targetPid:String){
        //不能转给自己,自己必须有岛，自己不能加岛，对方不能有岛，对方不能加岛，
        if (pid == targetPid) {
            throw RdiResponseCodeException (ResponseCode.SourceEqualsTarget)
        }
        if (mapper.isPlayerJoinAnyIsland(pid)) {
            throw RdiResponseCodeException (ResponseCode.SourceAlreadyJoinAnyIsland)
        }
        if (mapper.isPlayerOwnIsland(targetPid)) {
            throw RdiResponseCodeException(ResponseCode.TargetAlreadyOwnIsland)
        }
        if (mapper.isPlayerJoinAnyIsland(targetPid)) {
            throw RdiResponseCodeException (ResponseCode.TargetAlreadyJoinAnyIsland)
        }
        val iid = mapper.getPlayerOwnIslandId(pid)?:throw RdiResponseCodeException(ResponseCode.SourceNotOwnIsland)
        mapper.updateIslandPid(iid, targetPid)
    }
    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    fun changeLocation(pid:String, x: Double, y: Double, z: Double, w: Double, p: Double){
        //没有岛屿改不了
        val iid = mapper.getPlayerOwnIslandId(pid)?:throw RdiResponseCodeException( ResponseCode.SourceNotOwnIsland)
        mapper.updateIslandLocation(iid, x, y, z, w, p)
    }
    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    fun addMember(pid: String, mpid: String) {
        //不能添加自己
        if (pid == mpid)
            throw RdiResponseCodeException( ResponseCode.SourceEqualsTarget)
        //对方已经有岛了，不加
        if (mapper.isPlayerOwnIsland(mpid)) {
            throw RdiResponseCodeException( ResponseCode.TargetAlreadyOwnIsland)
        }
        //对方已经进岛了，不加
        if (mapper.isPlayerJoinAnyIsland(mpid)) {
            throw RdiResponseCodeException( ResponseCode.TargetAlreadyJoinAnyIsland)
        }
        val iid = mapper.getPlayerOwnIslandId(pid)?:throw RdiResponseCodeException( ResponseCode.SourceNotOwnIsland)
        mapper.insertMember(mpid, iid)
    }
    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    fun removeMember(mpid: String) {
        //目标有自己的岛，不删
        if (mapper.isPlayerOwnIsland(mpid)) throw RdiResponseCodeException( ResponseCode.TargetAlreadyOwnIsland)
        //目标没进任何岛 不删
        val iid = mapper.getPlayerJoinIslandId(mpid)?:throw RdiResponseCodeException( ResponseCode.TargetNotJoinAnyIsland)
        mapper.deleteMember(mpid, iid)
    }
}