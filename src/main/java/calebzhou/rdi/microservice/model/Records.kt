package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-05,21:39.
 */
data class RecordChat(val pid:String,val cont:String,val ts:Timestamp)
data class RecordDeath(val pid:String,val src:String,val ts:Timestamp)
data class RecordIdName(var id:String?=null,val pid:String,val pname:String,val ts:Timestamp)
data class RecordLogout(val pid:String,val ts:Timestamp)
data class RecordLogin(val pid:String,val ip:String,val geo:String,val ts:Timestamp)
//方块记录
data class RecordBlock(var id: Long?, val pid: String, val act :Int,
                  val bid: String, val world: String,
                  val x :Int, val y :Int, val z :Int, val ts: Timestamp)