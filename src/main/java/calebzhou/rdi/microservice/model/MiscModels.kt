package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,11:05.
 */
//rdi账号
class Account(var id: String, var pwd: String, var regIp: String, var regTime: Timestamp)


