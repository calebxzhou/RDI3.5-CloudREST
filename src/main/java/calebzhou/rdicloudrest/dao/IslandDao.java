package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.Island;
import calebzhou.rdicloudrest.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IslandDao {
    public static boolean has(String uuid) throws SQLException {
        ResultSet rs=DatabaseConnector.getPreparedStatement("select islandId from Island where playerUuid=?",uuid).executeQuery();
        return rs.next();
    }
    public static boolean joined(String uuid) throws SQLException{
        return DatabaseConnector.getPreparedStatement("select islandId from IslandMember where memberUuid = ?",uuid).executeQuery().next();
    }
    public static boolean join(String uuid,String targetIslandId) throws SQLException {
        return DatabaseConnector.getPreparedStatement("insert into IslandMember values (?,?)",targetIslandId,uuid).executeUpdate()==1;
    }
    public static boolean create(Island island) throws SQLException, IllegalAccessException {
        return SqlUtils.insertObjectToTable(island,Island.class)==1;
    }
    public static boolean delete(String islandId) throws SQLException{
        DatabaseConnector.getPreparedStatement("delete from IslandMember where islandId=?",islandId).executeUpdate();
        return DatabaseConnector.getPreparedStatement("delete from Island where islandId=?",islandId).executeUpdate()==1;
    }
    public static boolean quit(String islandId,String memberUuid) throws SQLException{
        return DatabaseConnector.getPreparedStatement("delete from IslandMember where islandId=? and memberUuid=?",islandId,memberUuid).executeUpdate()==1;
    }
    public static boolean locate(String islandId, CoordLocation location) throws SQLException{
        return DatabaseConnector.getPreparedStatement("update IslandMember set location=? where islandId=?",location,islandId).executeUpdate()==1;
    }
    public static String getIslandId(String ownerUuid) throws SQLException{
        ResultSet rs=DatabaseConnector.getPreparedStatement("select islandId from Island where ownerUuid=?",ownerUuid).executeQuery();
        if(!rs.next())
            return "null";
        return rs.getString("islandId");
    }
}
