package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.controller.BlockRecordController;
import calebzhou.rdicloudrest.controller.PlayerHomeController;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
@ServletComponentScan
@SpringBootApplication
public class RdicloudrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdicloudrestApplication.class, args);
	}
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
		return factory;
	}
}
