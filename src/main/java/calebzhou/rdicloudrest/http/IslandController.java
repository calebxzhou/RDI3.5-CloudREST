package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.dto.Island;
import calebzhou.rdicloudrest.utils.RandomUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/island/*")
@Slf4j
public class IslandController extends BasicServlet {
    enum IslandAction{
        //主动加入他人空岛
        join,
        //退出他人空岛
        quit,
        //邀请他人加入空岛
        invite,kick,
        //改变空岛传送点
        sethome,

    }
    //创建空岛 POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {
        super.doPost(req, resp);
        String pid = getPath(req);
        if (checkHasIsland(pid)||checkJoinedIsland(pid)){
            responseError(resp,"您不符合创建空岛的条件");
            return;
        }
        //随机空岛位置
        CoordLocation islandLocation = RandomUtils.getRandomCoordinate();
        //随机空岛id
        String iid = RandomUtils.getRandomId();
        Island island = new Island(iid, pid, islandLocation);
        boolean createResult = false;
        try {
            createResult = IslandDao.getInstance().create(island);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(createResult)
            write(resp,new Gson().toJson(island));
        else
            responseError(resp,"创建失败,请咨询服主");

    }

    //获取空岛对象 GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        String[] pathSplit = getPathWithAction(req);
        //岛id 或者 玩家id
        String type = pathSplit[0];
        String id = pathSplit[1];
        Island island = null;
        try {
            if(type.equals("iid")){
                island = IslandDao.getInstance().getIslandById(id);
            }else if(type.equals("pid")){
                island = IslandDao.getInstance().getIslandByPlayerUuid(id);
            }else{
                responseError(resp,"不存在的空岛参数!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        write(resp,new Gson().toJson(island));


        /*IslandAction action = null;
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
*/
    }
    //删除空岛 DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        super.doDelete(req, resp);
        String uid=getPath(req);
        if(!checkHasIsland(uid))return;
        try {
            write(resp, IslandDao.getInstance().delete(uid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getall() throws SQLException, IllegalAccessException {
        ResultSet query = DatabaseConnector.getPreparedStatement("select * from Island").executeQuery();
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
      /*  if(sql.endsWith("Island")) {

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
        }*/


    }

    private boolean checkHasIsland(String pid)  {
        boolean has = false;
        try {
            has = IslandDao.getInstance().getInstance().checkHasIsland(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return has;
    }
    private boolean checkJoinedIsland(String pid) {
        boolean joined = false;
        try {
            joined = IslandDao.getInstance().checkJoinedIsland(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joined;
    }
   /*
    //创建空岛,自己有空岛 或者 自己加入了别人的空岛,就不
    private void create() throws SQLException, IllegalAccessException {
        if (checkHasIsland()||checkJoinedIsland()) return;
        CoordLocation islandLocation = RandomUtils.getRandomCoordinate();
        boolean createResult = IslandDao.getInstance().create(new Island(RandomUtils.getRandomId(), pid, islandLocation));
        write(response,createResult?islandLocation:"创建失败,请联系服主!");
    }
    //删除,如果自己没有空岛就不
    private void delete() throws SQLException, IllegalAccessException {

    }
    //加入他人空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void join() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || checkJoinedIsland())return;
        if(iid.length()!=8){
            write(response,"空岛ID错误!加入失败");
            return;
        }
        write(response,
                IslandDao.getInstance().joinOtherIsland(pid, iid));
    }
    //退出空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void quit() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || !checkJoinedIsland())return;
        write(response,
                IslandDao.getInstance().quit(pid));
    }
    //邀请他人加入空岛,如果自己没有空岛 或者 自己加入了别人的空岛,就不
    private void invite() throws SQLException, IllegalAccessException {
        if(!checkHasIsland()|| checkJoinedIsland())return;
        String iid=IslandDao.getInstance().getIslandIdByPlayerUuid(pid);
        write(response,
                "告诉您的朋友输入指令/island join-"+iid+"即可加入,只能把指令告诉您的朋友,不要随意泄露指令"
                );
    }
    private void get() throws SQLException {
        String iid=IslandDao.getInstance().getIslandIdByPlayerUuid(pid);
    }
    //将玩家踢出空岛,如果自己没有空岛(岛主不是自己),就不
    private void kick() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() || checkJoinedIsland())return;

    }
    //回到空岛,如果自己没有空岛 或者 自己没加入别人的空岛 就不
    private void home() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() && !checkJoinedIsland())
            return;
        String iid =IslandDao.getInstance().getIslandIdByPlayerUuid(pid);
        if(iid==null)
            iid=IslandDao.getInstance().getIslandIdJoined(pid);
        write(response,
                IslandDao.getInstance().getIslandById(iid).getLocation());
    }
    //重设空岛传送点,如果自己没有空岛(岛主不是自己) 就不
    private void sethome() throws SQLException, IllegalAccessException {
        if(!checkHasIsland())return;
        write(response,
                IslandDao.getInstance().locate(pid, CoordLocation.fromString(loca)));
    }*/
}
