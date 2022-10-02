package calebzhou.rdi.microservice.ctrler.pub;

import calebzhou.rdi.microservice.bot.ServerListPing;
import calebzhou.rdi.microservice.model.dto.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calebzhou on 2022-10-02,20:03.
 */
@RestController
@RequestMapping("/v37/public")
public class PublicCtrler2 {
    @RequestMapping(value = "/mc_players")
    public Object asd(HttpServletResponse response){
        response.addHeader("Content-Type","text/html");
        return ResultData.success(getPlayerList());
    }

    private static String getPlayerList() {
        ServerListPing ping = new ServerListPing("test3.davisoft.cn",26085);
        ServerListPing.StatusResponse data = null;
        try {
            data = ping.fetchData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> sample = new ArrayList<>();
        int  number = 0;
        try {
            sample = data != null ? data.getPlayers().getSample().stream().map(ServerListPing.Player::getName).toList() : new ArrayList<>();
            number = data.getPlayers().getOnline();
        } catch (NullPointerException e) {
            return "";
        }
        return "玩家列表："+number+"人，"+sample.toString()+"";
    }
}
