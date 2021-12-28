package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.constants.HomeAction;
import calebzhou.rdicloudrest.dao.PlayerHomeDao;
import calebzhou.rdicloudrest.model.PlayerHome;
import calebzhou.rdicloudrest.utils.RequestUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
/*
http://localhost:26888/home?action=SET&playerUuid=123456789123456789123456789123456789&homeName=abc&dimension=a&posX=1.0&posY=1.0&posZ=1.0&yaw=1.0&pitch=1.0
http://localhost:26888/home?action=GET&playerUuid=123456789123456789123456789123456789&homeName=abcd
* */
@WebServlet("/home")
public class PlayerHomeController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HomeAction action = null;
        try {
             action = Enum.valueOf(HomeAction.class, req.getParameter("action"));
        } catch (RuntimeException e) {
            resp.getWriter().append(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }

        switch (Objects.requireNonNull(action)){
            case GET -> homeGet(req, resp);
            case SET -> homeSet(req, resp);
            case DELETE -> homeDelete(req, resp);
        }


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    private void homeGet(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String playerUuid = req.getParameter("playerUuid");
        String homeName = req.getParameter("homeName");
        String json = null;
        //没指定家的名称，则获取全部
        if(homeName == null){
            List<PlayerHome> playerHomeList = null;
            try {
                playerHomeList = PlayerHomeDao.getHomeByUuid(playerUuid);
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }

            json = new Gson().toJson(playerHomeList);
        }else{
            PlayerHome playerHome = null;
            try {
                playerHome = PlayerHomeDao.getHomeByUuidHomeName(playerUuid,homeName);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            json = new Gson().toJson(playerHome);
        }
        System.out.println(json);
        resp.getWriter().append(json);
    }
    private void homeSet(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        PlayerHome home = RequestUtils.parseRequstJsonToObject(PlayerHome.class,req);
        try {
            resp.getWriter().append(
                    PlayerHomeDao.insertHome(home)
                            >0?"success":"failed");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    private void homeDelete(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        try {
            resp.getWriter().append(
            PlayerHomeDao.deleteHome(req.getParameter("playerUuid"),req.getParameter("homeName"))
            >0 ? "success":"failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
