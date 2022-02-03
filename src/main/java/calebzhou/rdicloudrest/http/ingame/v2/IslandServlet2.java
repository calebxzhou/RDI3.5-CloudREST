package calebzhou.rdicloudrest.http.ingame.v2;

import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.logic.IslandLogic;
import calebzhou.rdicloudrest.model.dto.Island;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/v2/island/*")
@Slf4j
public class IslandServlet2 extends BasicServlet {
    /**
     * 创建空岛 POST island/{usr}
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {
        super.doPost(req, resp);
        String usr =getPath(req);
        Island island = IslandLogic.createIsland(usr,resp);
        if(island!=null)
            responseSuccess(resp,"成功创建了岛屿.",island);
    }
}
