package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.dao.IslandDao;

public class DaoFactory {
    private static IslandDao instanceIslandDao = null;
    public static IslandDao getIslandDao(){
        if(instanceIslandDao==null)
            instanceIslandDao=new IslandDao();
        return instanceIslandDao;
    }
}
