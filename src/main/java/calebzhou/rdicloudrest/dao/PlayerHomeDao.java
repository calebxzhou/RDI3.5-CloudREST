package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.PlayerHome;
import calebzhou.rdicloudrest.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHomeDao {
    public static List<PlayerHome> getHomeByUuid(String uuid) throws SQLException, IllegalAccessException {
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from PlayerHome where playerUuid=?",uuid).executeQuery();
        ArrayList<PlayerHome> playerHomes=new ArrayList<>();
        while(rs.next()){
            playerHomes.add(SqlUtils.initializeObjectByResultSet(rs,PlayerHome.class));
        }
        return playerHomes;
    }
    public static PlayerHome getHomeByUuidHomeName(String uuid,String homeName) throws SQLException, IllegalAccessException {
        ResultSet rs=DatabaseConnector.getPreparedStatement("select * from PlayerHome where playerUuid=? and homeName=?",uuid,homeName).executeQuery();
        PlayerHome playerHome=new PlayerHome();
        if(rs.next()){
            playerHome = SqlUtils.initializeObjectByResultSet(rs,PlayerHome.class);
        }
        return playerHome;
    }

    public static int insertHome(PlayerHome home)throws SQLException{
        return DatabaseConnector.getPreparedStatement("insert into PlayerHome values (?,?,?,?,?,?,?,?,?)",
                home.getPlayerUuid(),home.getHomeName(),home.getDimension(),
                home.getPosX(), home.getPosY(), home.getPosZ(),
                home.getYaw(), home.getPitch(),home.getComment()).executeUpdate();
    }
    public static int deleteHome(String uuid,String homeName)throws SQLException{
        return DatabaseConnector.getPreparedStatement("delete from PlayerHome where playerUuid=? and homeName=?"
        ,uuid,homeName).executeUpdate();
    }
}
