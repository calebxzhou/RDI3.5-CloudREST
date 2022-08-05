package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.utils.DateUtil;
import calebzhou.rdicloudrest.utils.ServerListPing;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ServletComponentScan
@SpringBootApplication
public class App extends SpringBootServletInitializer {
	public static final boolean DEBUG = false;
	public static final int VERSION =0x350;
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
		//注册机器人

		/*DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(BotLoader.class);
		beanFactory.registerBeanDefinition("botLoader", builder.getBeanDefinition());
		BotLoader bot = beanFactory.getBean(BotLoader.class);
		bot.initBot().login();*/
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]*"));
		return factory;
	}

	public static Bot BOT;
//机器人——————————————
	public static final String CMD_PREFIX = "r#";
	@Bean
	public Bot initBot() {
		Bot bot = BotFactory.INSTANCE.newBot(3168960758L, "69@QQ.com", new BotConfiguration() {
			{
				fileBasedDeviceInfo("deviceInfo.json");
				setProtocol(MiraiProtocol.ANDROID_PAD);
			}
		});
		//自动同意好友申请
		bot.getEventChannel().subscribeAlways(NewFriendRequestEvent.class,NewFriendRequestEvent::accept);

		bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, friendMessageEvent -> {
			Friend friend = friendMessageEvent.getFriend();
			friend.sendMessage(String.format("%s好，%s~", DateUtil.getTimePeriod(),friend.getNick()));
			String content = friendMessageEvent.getMessage().contentToString();
			if(!content.startsWith(CMD_PREFIX)) return;
			String cmd = content.replace(CMD_PREFIX,"").replace("，",",");
			String arg = StringUtils.substringAfter(cmd,"=");
			switch (StringUtils.substringBefore(cmd,"=")){
				//获取服务器在线人数
				case "list"-> sendPlayerList(friend);
				case "uuid"-> calcUuid(friend,arg);
			}



		});
		bot.login();
		BOT=bot;
		return bot;
	}

	private void calcUuid(Friend friend,String name) {
		String uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)).toString().replace("-", "");
		friend.sendMessage(uuid);
	}

	private void sendPlayerList(Friend friend) {
		ServerListPing ping = new ServerListPing("test3.davisoft.cn",26085);
		ServerListPing.StatusResponse data = null;
		try {
			data = ping.fetchData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> sample = data != null ? data.getPlayers().getSample().stream().map(ServerListPing.Player::getName).toList() : new ArrayList<>();
		int  number = data.getPlayers().getOnline();
		friend.sendMessage("当前在线"+number+"人："+sample.toString());
	}
//————————————————————
}
