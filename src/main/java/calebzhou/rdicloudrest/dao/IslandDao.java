package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.dto.Island;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
public class IslandDao {
    private static final IslandDao instance = new IslandDao();
    public static IslandDao getInstance(){
        return instance;
    }
    private HashMap<String, Island> islandMap = new HashMap<>();//岛ID vs 岛
    private HashMap<String, String> ownIslandMap = new HashMap<>();//玩家ID  vs  岛ID
    private Multimap<String, String> memberMap = LinkedHashMultimap.create();//岛ID  vs  成员ID

    public IslandDao() {
        try {
            initCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initCache() throws SQLException {
        log.info("载入空岛缓存");
        ResultSet rs = DatabaseConnector.getPreparedStatement("SELECT * FROM Island").executeQuery();
        while(rs.next()){
            String iid=rs.getString(1);
            String pid=rs.getString(2);
            Island island = new Island(
                    iid,pid,
                    rs.getString(3),
                    rs.getTimestamp(4)
                    );
            islandMap.put(iid,island);
            ownIslandMap.put(pid,iid);
        }
        log.info("载入空岛成员缓存");
        rs=DatabaseConnector.getPreparedStatement("SELECT * FROM IslandMember").executeQuery();
        while(rs.next()){
            memberMap.put(rs.getString(1),rs.getString(2));
        }
    }

    //是否拥有空岛
    public boolean checkHasIsland(String pid) throws SQLException {
        return DatabaseConnector.getPreparedStatement("SELECT 1 FROM Island WHERE ownerUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //是否加入他人的空岛
    public boolean checkJoinedIsland(String pid) throws SQLException {
        return DatabaseConnector.getPreparedStatement("SELECT 1 FROM IslandMember WHERE memberUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //通过空岛id获取空岛对象
    public Island getIslandById(String islandId) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where islandId=?",islandId).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过玩家id获取空岛
    public Island getIslandByPlayerUuid(String ownerUuid) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where ownerUuid=?",ownerUuid).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //通过玩家id获取加入他人的空岛id
    public String getIslandIdJoined(String memberUuid) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select islandId from IslandMember where memberUuid=?",memberUuid).executeQuery();
        if(!rs.next())
            return "null";
        return rs.getString("islandId");
    }
    //加入他人的空岛
    public boolean joinOtherIsland(String uuid, String targetIslandId) throws SQLException {
        return DatabaseConnector.getPreparedStatement("insert into IslandMember values (?,?)",targetIslandId,uuid).executeUpdate()==1;
    }
    public boolean create(Island island) throws SQLException {
        try {
            return SqlUtils.insertObjectToTable(island,Island.class)==1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(String iid) throws SQLException {
        DatabaseConnector.getPreparedStatement("delete from IslandMember where islandId=?",iid);
        return DatabaseConnector.getPreparedStatement("delete from Island where islandId=?",iid).executeUpdate()==1 ;
    }
    public boolean quit(String memberUuid) throws SQLException{
        return DatabaseConnector.getPreparedStatement("delete from IslandMember where memberUuid=?",memberUuid).executeUpdate()==1;
    }
    public boolean locate(String pid, CoordLocation location) throws SQLException{
        return DatabaseConnector.getPreparedStatement("update Island set location=? where ownerUuid=?",location.toString(),pid).executeUpdate()==1;
    }


}
