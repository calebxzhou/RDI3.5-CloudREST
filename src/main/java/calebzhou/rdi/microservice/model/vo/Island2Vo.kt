package calebzhou.rdi.microservice.model.vo

import calebzhou.rdi.microservice.model.entity.Island2Loca
import calebzhou.rdi.microservice.model.entity.RdiPlayer
import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-07,22:47.
 */
data class Island2Vo(val iid:Int,
                     val owner:RdiPlayer,
                     val createTime:Timestamp,
                     val loca:Island2Loca, val crews:List<Island2CrewVo> ) {
}