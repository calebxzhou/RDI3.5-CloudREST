package calebzhou.rdi.microservice.model.entity

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,21:11.
 */
/**
 *  岛屿信息
*/

//岛屿位置
data class Island2Loca (var id :Int ,var iid :Int,
var x :Double, var y :Double, var z :Double, var w :Double, var p :Double)
//岛屿成员
data class Island2Crew (var id:Int,var pid:String,var iid:Int,var ts:Timestamp)
//岛屿
data class Island2(var iid:Int?=null, var pid:String?=null, var ts: Timestamp?=null,
              var loca:Island2Loca?=null, var crews:List<Island2Crew>?=null){
}
