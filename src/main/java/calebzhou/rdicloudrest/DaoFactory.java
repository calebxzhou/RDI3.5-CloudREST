package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.dao.IslandDao;

import java.sql.SQLException;

public class DaoFactory {
    private static IslandDao instanceIslandDao = null;
    public static IslandDao getIslandDao(){
        if(instanceIslandDao==null)
            instanceIslandDao=new IslandDao();
        return instanceIslandDao;
    }
    public static void initCaches(){
        try {
            getIslandDao().initCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
