package calebzhou.rdi.microservice.model.entity

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-07,22:50.
 */
data class RdiPlayer(val pid:String,val name:String,val regTime: Timestamp) {
}