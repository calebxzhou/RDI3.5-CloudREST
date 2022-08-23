package calebzhou.rdicloudrest.ctrler;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.Island2;
import calebzhou.rdicloudrest.model.Island2Loca;
import calebzhou.rdicloudrest.utils.TimeUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/island2")
public class Island2Ctrler {

    //玩家是否拥有岛屿
    public boolean isPlayerOwnIsland(String pid){
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM Island2 WHERE pid = ? LIMIT 1)")){
            ps.setString(1,pid);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int result = rs.getInt(1);
                    return result == 1;
                }else
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //玩家是否加入岛屿
    public boolean isPlayerJoinIsland(String pid){
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = ? LIMIT 1)")){
            ps.setString(1,pid);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int result = rs.getInt(1);
                    return result == 1;
                }else
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isMemberInIsland(String pid,String iid){
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = ? and iid=? LIMIT 1)")){
            ps.setString(1,pid);
            ps.setString(2,iid);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int result = rs.getInt(1);
                    return result == 1;
                }else
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //获取玩家拥有的岛屿id
    public int getPlayerIslandIdOwn(String pid){
        if(!isPlayerOwnIsland(pid))
            return 0;
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("select iid from Island2 where pid=?", pid);
             ResultSet rs = ps.executeQuery();) {
            rs.next();
            int iid = rs.getInt("iid");
            ps.getConnection().close();
            return iid;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //获取玩家加入的岛屿id
    public int getPlayerIslandIdJoin(String pid){
        if(!isPlayerJoinIsland(pid))
            return 0;
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("select iid from Island2Crew where pid=?", pid);
             ResultSet rs = ps.executeQuery();) {
            rs.next();
            int iid = rs.getInt("iid");
            ps.getConnection().close();
            return iid;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public Island2Loca getIslandLocaByIid(int iid){
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("select * from Island2Loca where iid=?", iid);
             ResultSet rs = ps.executeQuery();) {
            if(!rs.next())
                return null;
            Island2Loca loca = new Island2Loca(
                    rs.getInt("id"),
                    rs.getInt("iid"),
                    rs.getDouble("x"),
                    rs.getDouble("y"),
                    rs.getDouble("z"),
                    rs.getDouble("w"),
                    rs.getDouble("p")
                    );
            ps.getConnection().close();
            return loca;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //获取岛屿基本信息 提供PID 成功返回岛屿数据失败返回0
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public String getIsland(@PathVariable String pid){
        boolean ownIsland;
        if(isPlayerOwnIsland(pid))
            ownIsland = true;
        else if(isPlayerJoinIsland(pid))
            ownIsland=false;
        else
            return "0";
        String sql  = ownIsland? "SELECT * from Island2 where pid=?":"select * from Island2Crew where pid=?";
        String resultToReturn;
        try(Connection connection=DatabaseConnector.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,pid);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return "0";
            int iid = rs.getInt("iid");
            Island2Loca loca = getIslandLocaByIid(iid);
            resultToReturn = new Island2(iid,rs.getString("pid"),rs.getTimestamp("ts"),loca).toString();
            rs.close();
            return resultToReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }
    }

    //提供玩家pid创建新的空岛，成功返回iid，失败返回0
    @RequestMapping(value = "/{pid}",method = RequestMethod.POST)
    public int createIsland(@PathVariable String pid){
        if(isPlayerOwnIsland(pid) || isPlayerJoinIsland(pid)){
            return 0;
        }
        try(PreparedStatement ps = DatabaseConnector.getPreparedStatement("insert into Island2 (pid,  ts) VALUES (?,?)", pid, TimeUtils.getNow())){
            ps.executeUpdate();
            ps.getConnection().close();
            return getPlayerIslandIdOwn(pid);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //提供玩家pid删除空岛 1成功0失败
    @RequestMapping(value = "/{pid}",method = RequestMethod.DELETE)
    public int deleteIsland(@PathVariable String pid){
        //没岛不删 加岛不删
        if(!isPlayerOwnIsland(pid)||isPlayerJoinIsland(pid))
            return 0;
        try(PreparedStatement ps = DatabaseConnector.getPreparedStatement("delete from Island2 where pid=?",pid)){
            int i = ps.executeUpdate();
            ps.getConnection().close();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //提供玩家pid修改空岛传送点，参数x,y,z,w,p坐标 0失败1成功
    @RequestMapping(value = "/{pid}",method = RequestMethod.PUT)
    public int changeLocation(@PathVariable String pid, @RequestParam double x,@RequestParam double y,@RequestParam double z,
                              @RequestParam double w,@RequestParam double p){
        //没有岛屿改不了 加了别人的也改不了
        if(!isPlayerOwnIsland(pid) || isPlayerJoinIsland(pid)){
            return 0;
        }
        int iid = getPlayerIslandIdOwn(pid);
        try(PreparedStatement ps = DatabaseConnector.getPreparedStatement("update Island2Loca set x=?,y=?,z=?,w=?,p=? where iid=?",x,y,z,w,p,iid)){
            int i = ps.executeUpdate();
            ps.getConnection().close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //添加空岛成员，提供岛主pid和成员pid,1成功0失败
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.POST)
    public int addMember(@PathVariable String pid,@PathVariable String mpid){
        //没岛不加
        if(!isPlayerOwnIsland(pid))
            return 0;
        //不能添加自己
        if(pid.equals(mpid))
            return 0;
        //对方已经有岛了，不加
        if (isPlayerOwnIsland(mpid)) {
            return 0;
        }
        //对方已经进岛了，不加
        if(isPlayerJoinIsland(mpid)){
            return 0;
        }
        int iid = getPlayerIslandIdOwn(pid);
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("insert into Island2Crew (pid,iid) values (?,?)", mpid, iid)) {
            int i =ps.executeUpdate();
            ps.getConnection().close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //删除空岛成员，提供岛主pid和成员pid 1成功0失败
    @RequestMapping(value = "/crew/{pid}/{mpid}",method = RequestMethod.DELETE)
    public int removeMember(@PathVariable String pid,@PathVariable String mpid){
        //自己没岛不删
        if(!isPlayerOwnIsland(pid))
            return 0;
        //不能删除自己
        if(pid.equals(mpid))
            return 0;
        //对方有自己的岛，不删
        if(isPlayerOwnIsland(mpid))
            return 0;
        //对方没进入任何一个岛，不删
        if(!isPlayerJoinIsland(mpid))
            return 0;
        int iid = getPlayerIslandIdOwn(pid);
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("delete from Island2Crew where pid=? and iid=?", mpid, iid)) {
            int i = ps.executeUpdate();
            ps.getConnection().close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }









}
