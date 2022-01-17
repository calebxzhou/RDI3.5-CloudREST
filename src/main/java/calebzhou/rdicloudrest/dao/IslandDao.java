package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.dto.Island;
import calebzhou.rdicloudrest.model.dto.IslandBookmark;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class IslandDao {
    private static final IslandDao instance = new IslandDao();
    public static IslandDao getInstance(){
        return instance;
    }
    private HashMap<String, Island> islandMap = new HashMap<>();//岛ID vs 岛
    private HashMap<String, IslandBookmark> bookmarkMap = new HashMap<>();//岛ID vs 位置书签
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
        log.info("载入位置书签缓存");
        rs=DatabaseConnector.getPreparedStatement("SELECT * from IslandBookmark").executeQuery();
        while (rs.next()){
            String iid=rs.getString(1);
            IslandBookmark bookmark = new IslandBookmark(
                    iid,
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getTimestamp(5)
                    );
            bookmarkMap.put(iid,bookmark);
        }
    }
    //是否拥有空岛
    public boolean checkHasIsland(String pid) throws SQLException {
        return this.ownIslandMap.containsKey(pid);//DatabaseConnector.getPreparedStatement("SELECT 1 FROM Island WHERE ownerUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //是否加入他人的空岛
    public boolean checkJoinedIsland(String pid) throws SQLException {
        return this.memberMap.containsValue(pid);//DatabaseConnector.getPreparedStatement("SELECT 1 FROM IslandMember WHERE memberUuid =? LIMIT 1",pid).executeQuery().next();
    }
    //通过空岛id获取空岛对象
    public Island getIslandById(String islandId) throws SQLException{
        return this.islandMap.get(islandId);
        /*ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where islandId=?",islandId).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;*/
    }
    //通过玩家id获取空岛
    public Island getIslandByPlayerUuid(String ownerUuid) throws SQLException{
        return this.islandMap.get(this.ownIslandMap.get(ownerUuid));
        /*ResultSet rs=DatabaseConnector.getPreparedStatement("select * from Island where ownerUuid=?",ownerUuid).executeQuery();
        rs.next();
        try {
            return SqlUtils.initializeObjectByResultSet(rs,Island.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;*/
    }
    //TODO Untested
    //通过玩家id获取加入他人的空岛id
    public String getIslandIdJoined(String memberUuid) throws SQLException{
        return this.memberMap.entries().stream().filter(e -> e.getValue().equals(memberUuid)).map(Map.Entry::getKey).toList().get(0);
        /*ResultSet rs=DatabaseConnector.getPreparedStatement("select islandId from IslandMember where memberUuid=?",memberUuid).executeQuery();
        if(!rs.next())
            return "null";
        return rs.getString("islandId");*/
    }
    //加入他人的空岛
    public boolean joinOtherIsland(String pid, String targetIslandId) throws SQLException {
        boolean result = DatabaseConnector.getPreparedStatement("insert into IslandMember values (?,?)", targetIslandId, pid).executeUpdate() == 1;
        this.memberMap.put(targetIslandId,pid);
        return result;
    }
    public boolean create(Island island) throws SQLException {
        boolean result = SqlUtils.insertObjectToTable(island,Island.class)==1;
        this.islandMap.put(island.getIslandId(),island);
        this.ownIslandMap.put(island.getOwnerUuid(),island.getIslandId());
        return result;

    }
    public boolean delete(String iid) throws SQLException {
        DatabaseConnector.getPreparedStatement("delete from IslandMember where islandId=?",iid).executeUpdate();
        boolean result = DatabaseConnector.getPreparedStatement("delete from Island where islandId=?",iid).executeUpdate()==1;
        String pid = this.islandMap.get(iid).getOwnerUuid();
        this.islandMap.remove(iid);
        this.ownIslandMap.remove(pid);
        this.memberMap.removeAll(iid);
        return result;
    }
    public boolean quit(String memberUuid) throws SQLException{
         boolean result = DatabaseConnector.getPreparedStatement("delete from IslandMember where memberUuid=?",memberUuid).executeUpdate()==1;
         String iid = getIslandIdJoined(memberUuid);
         this.memberMap.remove(iid,memberUuid);
         return result;
    }
    public boolean sethome(String iid, CoordLocation location) throws SQLException{
        boolean result = DatabaseConnector.getPreparedStatement("update Island set location=? where islandId=?", location.toString(), iid).executeUpdate() == 1;
        Island island = this.islandMap.get(iid);
        island.setLocation(location.toString());
        this.islandMap.put(iid,island);
        return result;
    }

    public boolean addBookmark(IslandBookmark bookmark) throws SQLException{
        this.bookmarkMap.put(bookmark.getIslandId(),bookmark);
        boolean result = SqlUtils.insertObjectToTable(bookmark,IslandBookmark.class)==1;
        return result;
    }
    public boolean deleteBookmark(IslandBookmark bookmark) throws SQLException{
        this.bookmarkMap.remove(bookmark.getIslandId());
        boolean result = DatabaseConnector.getPreparedStatement("delete from IslandBookmark where islandId = ? and markName = ?",bookmark.getIslandId(),bookmark.getMarkName()).executeUpdate()==1;
        return result;
    }

}
