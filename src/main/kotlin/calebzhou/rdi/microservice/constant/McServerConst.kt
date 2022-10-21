package calebzhou.rdi.microservice.constant

import calebzhou.rdi.microservice.DEBUG

/**
 * Created by calebzhou on 2022-10-16,21:56.
 */
object McServerConst {
    val SERVER_ADDR = if (DEBUG) "127.0.0.1" else "test3.davisoft.cn"
    val GAMEPLAY_PORT = 26085
    val RCON_PORT = 26095

}