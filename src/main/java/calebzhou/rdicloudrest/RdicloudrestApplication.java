package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.controller.BlockRecordController;
import calebzhou.rdicloudrest.controller.PlayerHomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
@ServletComponentScan
@SpringBootApplication
public class RdicloudrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdicloudrestApplication.class, args);
	}
	/*@Bean
	public ServletRegistrationBean homeBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(
				new PlayerHomeController(), "/home");
		bean.setLoadOnStartup(1);
		return bean;
	}
	@Bean
	public ServletRegistrationBean blockRecordBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(
				new BlockRecordController(), "/blockRecord");
		bean.setLoadOnStartup(1);
		return bean;
	}*/
}
