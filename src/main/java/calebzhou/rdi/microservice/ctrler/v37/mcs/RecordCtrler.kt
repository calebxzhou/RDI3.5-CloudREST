package calebzhou.rdi.microservice.ctrler.v37.mcs

import calebzhou.rdi.microservice.component.PassToken
import calebzhou.rdi.microservice.dao.RecordMapper
import calebzhou.rdi.microservice.model.*
import calebzhou.rdi.microservice.utils.RdiSerializer
import calebzhou.rdi.microservice.utils.TimeUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * Created by calebzhou on 2022-10-05,21:10.
 */
@RestController
@RequestMapping("/v37/mcs_game/record") //这个是专门为mc服务端准备的

class RecordCtrler(var mapper: RecordMapper) {
    //记录说话、指令
    @PassToken
    @RequestMapping(value = ["/chat"], method = [RequestMethod.POST])
    fun recordChat(@RequestParam pid: String, @RequestParam cont: String) {
        mapper.insertRecordChat(RecordChat(pid,cont,TimeUtils.getNow()))
    }


    //记录死亡
    @PassToken
    @RequestMapping(value = ["/death"], method = [RequestMethod.POST])
    fun recordDeath(@RequestParam pid: String, @RequestParam src: String) {
        mapper.insertRecordDeath(RecordDeath(pid,src,TimeUtils.getNow()))
    }

    //记录登入
    @PassToken
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun recordLogin(@RequestParam pid: String, @RequestParam ip: String, @RequestParam geo: String) {
        mapper.insertRecordLogin(RecordLogin(pid,ip,geo,TimeUtils.getNow()))
    }

    //记录UUID和昵称的对应关系（进入服务器时）
    @PassToken
    @RequestMapping(value = ["/idname"], method = [RequestMethod.POST])
    fun recordIdName(@RequestParam pid: String, name: String) {
        mapper.insertRecordIdName(RecordIdName(pid,name,TimeUtils.getNow()))
    }

    //记录登出
    @PassToken
    @RequestMapping(value = ["/logout"], method = [RequestMethod.POST])
    fun recordLogout(@RequestParam pid: String) {
        mapper.insertRecordLogout(RecordLogout(pid,TimeUtils.getNow()))
    }

    //方块破坏/放置
    @PassToken
    @RequestMapping(value = ["/block"], method = [RequestMethod.POST])
    fun recordBlock(
        @RequestParam(required = false, defaultValue = "00000000-0000-0000-0000-000000000000") pid: String,
        @RequestParam bid: String,
        @RequestParam act: Int,
        @RequestParam world: String,
        @RequestParam x: Int,
        @RequestParam y: Int,
        @RequestParam z: Int
    ) {
        mapper.insertRecordBlock(RecordBlock(null,pid,act, bid, world, x, y, z, TimeUtils.getNow()))
    }


}