package calebzhou.rdi.microservice

import mu.KotlinLogging
import okhttp3.OkHttpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Created by calebzhou on 2022-10-04,22:41.
 */
var DEBUG = false
    private set
val logger = KotlinLogging.logger {}
val HttpClient = OkHttpClient()
fun main(args: Array<String>) {
    if ("true" == System.getProperty("rdi.debug")) {
        DEBUG = true
    }
    runApplication<App>(*args)
}
@SpringBootApplication
class App
