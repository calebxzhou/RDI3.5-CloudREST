package calebzhou.rdi.microservice.model.entity

import calebzhou.rdi.microservice.annotation.ExcludeSerialize
import calebzhou.rdi.microservice.annotation.NoArg
import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,21:11.
 */
/**
 *  岛屿信息
*/

//岛屿位置
@NoArg
data class Island2Loca (@ExcludeSerialize var id :Int ,var iid :Int,
var x :Double, var y :Double, var z :Double, var w :Double, var p :Double)
//岛屿成员
@NoArg
data class Island2Crew (@ExcludeSerialize var id:Int,var pid:String,var iid:Int,var ts:Timestamp)
//岛屿
@NoArg
data class Island2(var iid:Int, var pid:String, var ts: Timestamp,
              var loca:Island2Loca, var crews:List<Island2Crew>){
}
