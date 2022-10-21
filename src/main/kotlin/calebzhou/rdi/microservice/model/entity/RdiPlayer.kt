package calebzhou.rdi.microservice.model.entity

import calebzhou.rdi.microservice.annotation.NoArg
import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-07,22:50.
 */
@NoArg
data class RdiPlayer(val pid:String,val name:String,val regTime: Timestamp) {
}