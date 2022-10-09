package calebzhou.rdi.microservice.ctrler.v37.pub

import calebzhou.rdi.microservice.service.ServerListPing
import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.model.ResponseData
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by calebzhou on 2022-10-05,21:13.
 */
@RestController
@RequestMapping("/v37/public")
class PingServerCtrler {
    @RequestMapping(value = ["/mc_players"])
    fun getMcPlayers(): ResponseData<String> {
        return ResponseData(ResponseCode.Success,getPlayerList())
    }

    private fun getPlayerList(): String {
        val ping = ServerListPing("test3.davisoft.cn", 26085)
        val data= ping.fetchData()
        val sample   = data.players.sample.stream().map { player -> player.name }.toList()
        val number = data.players.online
        return "玩家列表：" + number + "人，" + sample.toString()
    }
}