package calebzhou.rdi.microservice

import calebzhou.rdi.microservice.constant.McServerConst
import calebzhou.rdi.microservice.mcrcon.McRcon
import org.junit.jupiter.api.Test

/**
 * Created by calebzhou on 2022-10-16,22:01.
 */
class RdiTest {
    @Test
    fun testRcon(){
        val mcRcon = McRcon("127.0.0.1", 26095)
        mcRcon.authenticate("dmts_avia@964")

        val body =
            mcRcon.sendCommand("rdi-help")
            .body
        println(body)
    }
}