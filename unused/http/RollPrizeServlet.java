package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.model.dto.RollPrize;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/prize")
public class RollPrizeServlet extends BasicServlet{
    private static final String mc="minecraft:";
    private static final String ae="ae2:";

    float base=0.5f;
    public static final List<RollPrize> prizes = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        int rand14=RandomUtils.nextInt(1,4);
        int rand13=RandomUtils.nextInt(1,3);
        int count = Integer.parseInt(req.getParameter("count"));
        if(count>3)
            base=0.4f;
        if(count>10)
            base=0.375f;
        if(count>30)
            base=0.34f;
        prizes.add(new RollPrize("exp", RollPrize.Type.exp,"随机经验",base/1.5f, rand14));
        prizes.add(new RollPrize(mc+"dirt", RollPrize.Type.item,"泥土",base/2f,rand14));
        prizes.add(new RollPrize(mc+"jungle_sapling", RollPrize.Type.item,"丛林树苗",base/2.5f,rand13));
        prizes.add(new RollPrize(mc+"redstone_block", RollPrize.Type.item,"红石块",base/4f,rand13));
        prizes.add(new RollPrize(mc+"ancient_debris", RollPrize.Type.item,"远古残骸",base/5f,rand14));
        prizes.add(new RollPrize(mc+"creeper", RollPrize.Type.item,"闪电苦力怕",base/8f,rand14));
        write(resp,new Gson().toJson(prizes.toArray()));
    }
}
