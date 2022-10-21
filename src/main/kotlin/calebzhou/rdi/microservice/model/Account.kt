package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-09,22:45.
 */
data class Account(var id: String, var pwd: String, var regIp: String, var regTime: Timestamp)
