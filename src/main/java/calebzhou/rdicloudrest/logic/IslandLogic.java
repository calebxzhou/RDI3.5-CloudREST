package calebzhou.rdicloudrest.logic;

import calebzhou.rdicloudrest.DaoFactory;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.module.island.Island;
import calebzhou.rdicloudrest.utils.RandomUtils;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static calebzhou.rdicloudrest.http.BasicServlet.*;

public class IslandLogic {
    public static boolean checkHasIsland(String pid)  {
        boolean has = false;
        has = DaoFactory.getIslandDao().checkHasIsland(pid);

        return has;
    }
    public static boolean checkJoinedIsland(String pid) {
        boolean joined = false;
        joined = DaoFactory.getIslandDao().checkJoinedIsland(pid);
        return joined;
    }
    public static Island createIsland(String pid, HttpServletResponse resp){
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
    /**
     * @param pid 岛主id
     * @param mid 岛员id
     * */
    public static boolean deleteMember(String pid,String mid, HttpServletResponse resp){
        if (checkHasIsland(pid)){
            responseErrorHasIsland(resp,true);
            return false;
        }
        if(checkJoinedIsland(pid)) {
            responseErrorJoinedIsland(resp,true);
            return false;
        }
        String iid = DaoFactory.getIslandDao().getIslandIdByPlayerUuid(pid);
        return deleteMember(iid,mid);

    }
    public static boolean deleteMember(String iid,String mid){

        try {
            return DaoFactory.getIslandDao().deleteMember(iid,mid);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
