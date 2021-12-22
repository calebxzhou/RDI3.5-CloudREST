package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.controller.home.PlayerHomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RdicloudrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdicloudrestApplication.class, args);
	}
	@Bean
	public ServletRegistrationBean exampleServletBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(
				new PlayerHomeController(), "/home");
		bean.setLoadOnStartup(1);
		return bean;
	}
}
