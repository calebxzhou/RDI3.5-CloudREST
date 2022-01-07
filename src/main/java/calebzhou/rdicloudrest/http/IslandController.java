package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.constants.IslandStatus;
import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.model.IslandMember;
import calebzhou.rdicloudrest.model.record.GenericRecord;
import calebzhou.rdicloudrest.model.record.RecordType;
import calebzhou.rdicloudrest.utils.RandomUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        //获取所有
        getall,
        getid
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
                case getall -> getall();
            }
        }catch (RuntimeException | SQLException | IllegalAccessException exception){
            write(resp,exception.getMessage());
            exception.printStackTrace();
        }

    }
    private void getall() throws SQLException, IllegalAccessException {
        String sql = request.getParameter("query" );
        ResultSet query = DatabaseConnector.getPreparedStatement(sql).executeQuery();
        if(sql.endsWith("Island")) {
            ArrayList<Island> list = new ArrayList<>();
            while(query.next()){
                Island island=new Island(
                        query.getString(1),
                        query.getString(2),
                        query.getString(3),
                        query.getTimestamp(4)
                );
                list.add(island);
            }
            ResponseUtils.write(response,new Gson().toJson(list));
        }else if(sql.endsWith("IslandMember")){
            ArrayList<IslandMember> list = new ArrayList<>();
            while(query.next()){
                IslandMember member = new IslandMember();
                member.setIslandId(query.getString(1));
                member.setMemberUuid(query.getString(2));
                list.add(member);
            }
            ResponseUtils.write(response,new Gson().toJson(list));
        }


    }
    private boolean checkHasIsland() throws SQLException {
        boolean has = IslandDao.checkHasIsland(pid);
        if(has){
            write(response, IslandStatus.OWN_ISLAND);
            return true;
        }else{
            write(response,IslandStatus.NOT_OWN_ISLAND);
            return false;
        }
    }
    private boolean checkJoinedIsland() throws SQLException{
        boolean joined = IslandDao.checkJoinedIsland(pid);
        if(joined){
            write(response,IslandStatus.JOIN_ISLAND);
            return true;
        }else{
            write(response, IslandStatus.NOT_JOIN_ISLAND);
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
        if(iid.length()!=8){
            write(response,"空岛ID错误!加入失败");
            return;
        }
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
    private void get() throws SQLException {
        String iid=IslandDao.getIslandIdByPlayerUuid(pid);
    }
    //将玩家踢出空岛,如果自己没有空岛(岛主不是自己),就不
    private void kick() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() || checkJoinedIsland())return;

    }
    //回到空岛,如果自己没有空岛 或者 自己没加入别人的空岛 就不
    private void home() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() && !checkJoinedIsland())
            return;
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
