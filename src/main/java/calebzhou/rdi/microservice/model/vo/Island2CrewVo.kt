package calebzhou.rdi.microservice.model.vo

import calebzhou.rdi.microservice.annotation.NoArg
import calebzhou.rdi.microservice.model.entity.RdiPlayer
import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-07,23:04.
 */
@NoArg
data class Island2CrewVo(val player: RdiPlayer,val joinTime:Timestamp?)
