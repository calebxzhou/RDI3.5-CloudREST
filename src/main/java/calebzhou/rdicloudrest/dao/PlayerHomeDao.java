package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.PlayerHome;
import calebzhou.rdicloudrest.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHomeDao {
    //通过uuid获取家列表
    public static List<PlayerHome> getHomeByUuid(String uuid) throws SQLException, IllegalAccessException {
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from PlayerHome where playerUuid=?",uuid).executeQuery();
        ArrayList<PlayerHome> playerHomes=new ArrayList<>();
        while(rs.next()){
            playerHomes.add(SqlUtils.initializeObjectByResultSet(rs,PlayerHome.class));
        }
        return playerHomes;
    }
    //通过uuid和家名称 获取 家对象
    public static PlayerHome getHomeByUuidHomeName(String uuid,String homeName) throws SQLException, IllegalAccessException {
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from PlayerHome where playerUuid=? and homeName=?",uuid,homeName).executeQuery();
        PlayerHome playerHome=new PlayerHome();
        if(rs.next()){
            playerHome = SqlUtils.initializeObjectByResultSet(rs,PlayerHome.class);
        }
        return playerHome;
    }

    public static int insertHome(PlayerHome home) throws SQLException, IllegalAccessException {
        //TODO
        return SqlUtils.insertObjectToTable(home,PlayerHome.class);
    }
    public static int deleteHome(String uuid,String homeName)throws SQLException{
        return DatabaseConnector.getPreparedStatement("delete from PlayerHome where playerUuid=? and homeName=?"
        ,uuid,homeName).executeUpdate();
    }
}
