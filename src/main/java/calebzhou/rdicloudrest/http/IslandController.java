package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.DaoFactory;
import calebzhou.rdicloudrest.dao.IslandDao;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.dto.ApiAction;
import calebzhou.rdicloudrest.model.dto.Island;
import calebzhou.rdicloudrest.model.dto.IslandBookmark;
import calebzhou.rdicloudrest.utils.RandomUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/island/*")
@Slf4j
public class IslandController extends BasicServlet {
    enum IslandAction{
        //主动加入他人空岛
        join,
        //退出他人空岛
        quit,
        kick,

    }
    /**POST
     * 创建空岛 create/{pid}
     * 新增传送点 bookmark?obj={IslandBookMark}
     * TESTED
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {
        super.doPost(req, resp);
            String pid =getPath(req);
            Island island = createIsland(pid,resp);
            if(island==null)
                responseError(resp,"创建岛屿失败, 您不符合创建的条件",null);
            else
                responseSuccess(resp,"成功创建了岛屿.",island);
    }

    /**
     * 获取空岛对象 GET
     * iid/{岛ID}  pid/{玩家uuid}
     * TESTED
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        //岛id 或者 玩家id
        String id = getPath(req);
        String idType=req.getParameter("idType");
        if(idType==null){
            responseErrorParams(resp,"id格式");
            return;
        }
        Island island = null;
        if(idType.equals("iid")){
            island = DaoFactory.getIslandDao().getIslandById(id);
        }else if(idType.equals("pid")){
            island = DaoFactory.getIslandDao().getIslandByPlayerUuid(id);
        }else{
            responseErrorParams(resp,"");
            return;
        }

        if(island==null)
            responseError(resp,"无法获取空岛信息",null);
        else
            responseSuccess(resp,"成功获取空岛信息",island);


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
    /**
     * 删除空岛 DELETE uid
     * TESTED
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        super.doDelete(req, resp);
        String uid=getPath(req);
        if(!checkHasIsland(uid)){
            responseErrorHasIsland(resp,false);
            return;
        }
        boolean delete=false;
        try {
            delete = DaoFactory.getIslandDao().delete(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(delete)
            responseSuccess(resp,"删除成功",null);
        else
            responseErrorHasIsland(resp,false);
    }

    /**
     * 修改空岛位置 put
     * TESTED
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        super.doPut(req, resp);
        String pid=getPath(req);
        if(!checkHasIsland(pid)) {
            responseErrorHasIsland(resp,false);
            return;
        }
        String locas = req.getParameter("location");
        if(locas==null){
            responseError(resp,"参数错误",null);
            return;
        }
        CoordLocation location;
        try {
            location = CoordLocation.fromString(locas);
        } catch (ArrayIndexOutOfBoundsException e) {
            responseError(resp,"位置格式错误",null);
            return;
        }
        String iid = DaoFactory.getIslandDao().getIslandByPlayerUuid(pid).getIslandId();
        try {
            boolean b = DaoFactory.getIslandDao().sethome(iid,location);
            if(b)
                responseSuccess(resp,"成功修改了您的空岛传送点为"+location.toString(),null);
            else
                responseErrorHasIsland(resp,false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkHasIsland(String pid)  {
        boolean has = false;
            has = DaoFactory.getIslandDao().checkHasIsland(pid);

        return has;
    }
    private boolean checkJoinedIsland(String pid) {
        boolean joined = false;
            joined = DaoFactory.getIslandDao().checkJoinedIsland(pid);
        return joined;
    }
    private Island createIsland(String pid, HttpServletResponse resp){
        if (checkHasIsland(pid)){
            responseErrorHasIsland(resp,true);
            return null;
        }
        if(checkJoinedIsland(pid)) {
            responseErrorJoinedIsland(resp,true);
            return null;
        }
        //随机空岛位置
        CoordLocation islandLocation = RandomUtils.getRandomCoordinate();
        //随机空岛id
        String iid = RandomUtils.getRandomId();
        Island island = new Island(iid, pid, islandLocation);
        boolean createResult = false;
        try {
            createResult = DaoFactory.getIslandDao().create(island);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(createResult)
            return island;
        else
            return null;
    }
   /* private boolean createBookmark(IslandBookmark bookmark){
        String iid = bookmark.getIslandId();
        String pid = bookmark.getCreatorPid();
        if(!checkJoinedIsland(pid) || !checkHasIsland(pid)){
            return false;
        }
        boolean b = false;
        try {
            b = DaoFactory.getIslandDao().addBookmark(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }*/
   /*


    //加入他人空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void join() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || checkJoinedIsland())return;
        if(iid.length()!=8){
            write(response,"空岛ID错误!加入失败");
            return;
        }
        write(response,
                DaoFactory.getIslandDao().joinOtherIsland(pid, iid));
    }
    //退出空岛,如果自己有空岛 或者 自己加入了别人的空岛,就不
    private void quit() throws SQLException, IllegalAccessException {
        if(checkHasIsland() || !checkJoinedIsland())return;
        write(response,
                DaoFactory.getIslandDao().quit(pid));
    }
    //邀请他人加入空岛,如果自己没有空岛 或者 自己加入了别人的空岛,就不
    private void invite() throws SQLException, IllegalAccessException {
        if(!checkHasIsland()|| checkJoinedIsland())return;
        String iid=DaoFactory.getIslandDao().getIslandIdByPlayerUuid(pid);
        write(response,
                "告诉您的朋友输入指令/island join-"+iid+"即可加入,只能把指令告诉您的朋友,不要随意泄露指令"
                );
    }
    private void get() throws SQLException {
        String iid=DaoFactory.getIslandDao().getIslandIdByPlayerUuid(pid);
    }
    //将玩家踢出空岛,如果自己没有空岛(岛主不是自己),就不
    private void kick() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() || checkJoinedIsland())return;

    }
    //回到空岛,如果自己没有空岛 或者 自己没加入别人的空岛 就不
    private void home() throws SQLException, IllegalAccessException {
        if(!checkHasIsland() && !checkJoinedIsland())
            return;
        String iid =DaoFactory.getIslandDao().getIslandIdByPlayerUuid(pid);
        if(iid==null)
            iid=DaoFactory.getIslandDao().getIslandIdJoined(pid);
        write(response,
                DaoFactory.getIslandDao().getIslandById(iid).getLocation());
    }
    //重设空岛传送点,如果自己没有空岛(岛主不是自己) 就不
    private void sethome() throws SQLException, IllegalAccessException {
        if(!checkHasIsland())return;
        write(response,
                DaoFactory.getIslandDao().locate(pid, CoordLocation.fromString(loca)));
    }*/
}
