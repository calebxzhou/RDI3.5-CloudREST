package calebzhou.rdicloudrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class RdicloudrestApplication {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RdicloudrestApplication.class);
		app.run(args);
		initDaos();
	}

	public static void initDaos() {
		DaoFactory.getIslandDao();
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]*"));
		return factory;
	}
}
