package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.dto.ApiAction;
import calebzhou.rdicloudrest.model.dto.Island;
import calebzhou.rdicloudrest.model.dto.IslandBookmark;
import calebzhou.rdicloudrest.utils.RandomUtils;
import calebzhou.rdicloudrest.utils.RequestUtils;
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
    /**POST
     * 创建空岛 create/{pid}
     * 新增传送点 bookmark/{param obj=IslandBookMark}
     *
     *
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {
        super.doPost(req, resp);
        ApiAction action = getPathWithAction(req);
        if(action.getAction().equals("create")){
            String pid = action.getParam();
            Island island = createIsland(pid);
        }else if(action.getAction().equals("bookmark")){
            IslandBookmark bookmark = BasicServlet.parseRequstJsonToObject(IslandBookmark.class, req);

        }

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
    //修改空岛位置 put
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        super.doPut(req, resp);

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
    private Island createIsland(String pid){
        if (checkHasIsland(pid)||checkJoinedIsland(pid)){
            throw new IllegalArgumentException("您不符合创建空岛的条件");
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
            return island;
        else
            throw new IllegalArgumentException("创建失败,请咨询服主");
    }
    private boolean createBookmark(IslandBookmark bookmark){
        String iid = bookmark.getIslandId();
        String pid = bookmark.getCreatorPid();
        if(!checkJoinedIsland(pid) || !checkHasIsland(pid)){
            throw new IllegalArgumentException("您未加入空岛或者自己没有空岛。");
        }
    }
   /*


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
