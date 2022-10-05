package calebzhou.rdi.microservice

import mu.KotlinLogging
import okhttp3.OkHttpClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * Created by calebzhou on 2022-10-04,22:41.
 */
var DEBUG = false
val logger = KotlinLogging.logger {}
val HttpClient = OkHttpClient()
fun main(args: Array<String>) {
    if ("true" == System.getProperty("rdi.debug")) {
        DEBUG = true
    }
    SpringApplication.run(App::class.java, *args)
}

@SpringBootApplication
class App : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder? {
        return builder.sources(App::class.java)
    }

    /*@Bean
    fun webServerFactory(): ConfigurableServletWebServerFactory? {
        val factory = TomcatServletWebServerFactory()
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector ->
            connector.setProperty(
                "relaxedQueryChars",
                "|{}[]*"
            )
        })
        return factory
    }*/

}
