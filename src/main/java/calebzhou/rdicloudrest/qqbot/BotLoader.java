package calebzhou.rdicloudrest.qqbot;

import calebzhou.rdicloudrest.module.account.Account;
import calebzhou.rdicloudrest.module.account.AccountRepo;
import calebzhou.rdicloudrest.utils.ServerListPing;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BotLoader {
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
        bot.getEventChannel().subscribeAlways(FriendMessageEvent.class,friendMessageEvent -> {
            Friend friend = friendMessageEvent.getFriend();
            MessageChain message = friendMessageEvent.getMessage();
            String content = message.contentToString();
            if(!content.startsWith(CMD_PREFIX)){
                return;
            }
            String cmd = content.replace(CMD_PREFIX,"").replace("，",",");
            String arg = StringUtils.substringAfter(cmd,",");
            switch (StringUtils.substringBefore(cmd,",")){
                //获取服务器在线人数
                case "list"->sendPlayerList(friend);
                case "reg" -> reg(friend);
            }



        });
        return bot;
    }

    @Autowired
    AccountRepo accountRepo;
    private void reg(Friend friend) {
        //qq号
        long id = friend.getId();
        accountRepo.save(new Account(id, new Date()));
       /* String qq = RegisterController.qqCodeMap.get(code);
        if(qq==null || friend.getId()!=Long.parseLong(qq)){
            friend.sendMessage("验证码错误！");
            return;
        }*/

    }

    private void sendPlayerList(Friend friend) {
        ServerListPing ping = new ServerListPing("test3.davisoft.cn",26088);
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
}
