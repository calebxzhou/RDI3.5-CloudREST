package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.RandomUtils;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static calebzhou.rdicloudrest.utils.ResponseUtils.*;

@WebServlet("/island")
public class IslandController extends HttpServlet {
    enum IslandAction{
        //创建空岛
        create,
        //删除空岛
        delete,
        //主动加入他人空岛
        join,
        //退出他人空岛
        quit,
        //邀请他人加入空岛
        invite,kick,
        //返回空岛
        home,
        //改变空岛传送点
        sethome,

    }
    private HttpServletResponse response;
    private HttpServletRequest request;
    private String pid,iid,loca;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.request =req;
        this.response = resp;
        this.pid=req.getParameter("pid");
        this.iid=req.getParameter("iid");
        this.loca=req.getParameter("loca");
        IslandAction action = null;
        try {
            action = Enum.valueOf(IslandAction.class,req.getParameter("action"));
            switch (action){
                case create -> create();
                case delete -> delete();
                case join -> join();
                case quit -> quit();
                case invite -> invite();
                case kick -> kick();
                case home -> home();
                case sethome -> sethome();
            }
        }catch (RuntimeException | SQLException | IllegalAccessException exception){
            write(resp,exception.getMessage());
            exception.printStackTrace();
        }

    }
    private boolean checkHasIsland() throws SQLException {
        boolean has = IslandDao.checkHasIsland(pid);
        if(has){
            write(response,"您拥有空岛!");
            return true;
        }else{
            write(response,"您没有空岛!");
            return false;
        }
    }
    private boolean checkJoinedIsland() throws SQLException{
        boolean joined = IslandDao.checkJoinedIsland(pid);
        if(joined){
            write(response,"您加入了他人的空岛!");
            return true;
        }else{
            write(response,"您没有加入他人的空岛!");
            return false;
        }
    }
    //创建空岛,自己有空岛 或者 自己加入了别人的空岛,就不
    private void create() throws SQLException, IllegalAccessException {
        if (checkHasIsland()||checkJoinedIsland()) return;
        CoordLocation islandLocation = RandomUtils.getRandomCoordinate();
        boolean createResult = IslandDao.create(new Island(RandomUtils.getRandomId(), pid, islandLocation));
        write(response,createResult?islandLocation:"创建失败,请联系服主!");
    }
    //删除,如果自己没有空岛就不
    private void delete() throws SQLException, IllegalAccessException {
        if(!checkHasIsland())return;
        write(response,
                IslandDao.delete(pid));
    }
    //加入他人空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void join() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || checkJoinedIsland())return;
        write(response,
                IslandDao.joinOtherIsland(pid, iid));
    }
    //退出空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void quit() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || !checkJoinedIsland())return;
        write(response,
                IslandDao.quit(pid));
    }
    //邀请他人加入空岛,如果自己没有空岛 或者 自己加入了别人的空岛,就不
    private void invite() throws SQLException, IllegalAccessException {
        if(!checkHasIsland()|| checkJoinedIsland())return;
        String iid=IslandDao.getIslandIdByPlayerUuid(pid);
        write(response,
                "告诉您的朋友输入指令/island join-"+iid+"即可加入,只能把指令告诉您的朋友,不要随意泄露指令"
                );
    }
    //将玩家踢出空岛,如果自己没有空岛(岛主不是自己),就不
    private void kick() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() || checkJoinedIsland())return;

    }
    //回到空岛,如果自己没有空岛 或者 自己没加入别人的空岛 就不
    private void home() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() || !checkJoinedIsland())return;
        String iid =IslandDao.getIslandIdByPlayerUuid(pid);
        if(iid==null)
            iid=IslandDao.getIslandIdJoined(pid);
        write(response,
                IslandDao.getIslandById(iid).getLocation());
    }
    //重设空岛传送点,如果自己没有空岛(岛主不是自己) 就不
    private void sethome() throws SQLException, IllegalAccessException {
        if(!checkHasIsland())return;
        write(response,
                IslandDao.locate(pid, CoordLocation.fromString(loca)));
    }
}
