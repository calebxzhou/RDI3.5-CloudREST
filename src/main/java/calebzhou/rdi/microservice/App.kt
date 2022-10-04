package calebzhou.rdi.microservice

import mu.KotlinLogging
import org.apache.catalina.connector.Connector
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean

/**
 * Created by calebzhou on 2022-10-04,22:41.
 */
var DEBUG = false
val logger = KotlinLogging.logger {}
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

    @Bean
    fun webServerFactory(): ConfigurableServletWebServerFactory? {
        val factory = TomcatServletWebServerFactory()
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector ->
            connector.setProperty(
                "relaxedQueryChars",
                "|{}[]*"
            )
        })
        return factory
    }

}
