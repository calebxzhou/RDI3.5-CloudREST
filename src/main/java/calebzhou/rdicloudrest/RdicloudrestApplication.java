package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.qqbot.BotLoader;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class RdicloudrestApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RdicloudrestApplication.class);
		app.run(args);
		//注册机器人
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(BotLoader.class);
		beanFactory.registerBeanDefinition("botLoader", builder.getBeanDefinition());
		BotLoader bot = beanFactory.getBean(BotLoader.class);
		bot.initBot().login();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RdicloudrestApplication.class);
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]*"));
		return factory;
	}
}
