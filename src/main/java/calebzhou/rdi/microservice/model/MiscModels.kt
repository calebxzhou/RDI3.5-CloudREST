package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,11:05.
 */
//rdi账号
class Account(var id: String, var pwd: String, var regIp: String, var regTime: Timestamp)
//方块记录
class RecordBlock(var id: Long, var pid: String, var act :Int,
                  var bid: String, var world: String,
                  var x :Int, var y :Int, var z :Int, var ts: Timestamp)

