package calebzhou.rdi.microservice.ctrler.v37.mcs

import calebzhou.rdi.microservice.model.ResponseData
import calebzhou.rdi.microservice.service.IslandService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by calebzhou on 2022-10-05,7:52.
 */
@RestController
@RequestMapping("/v37/island")
class Island1Ctrler(var service:IslandService) {

    //提供玩家pid获取空岛坐标x,y,z
    @RequestMapping(value = ["/{pid}"], method = [RequestMethod.GET])
    fun getIsland(@PathVariable pid: String): ResponseData<String> {
        return ResponseData(200,"1",service.getIsland1(pid))
    }
}