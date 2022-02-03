package calebzhou.rdicloudrest.dao;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class ProtectDao implements Cacheable{
    private final Multimap<String,String> protectOwnMap = LinkedHashMultimap.create();//玩家id vs 保护区id
    private final Multimap<String,String> protectMap = LinkedHashMultimap.create();//保护区id vs 保护区范围


    public ProtectDao() {
        try {
            initCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initCache() throws SQLException {
        log.info("载入保护区缓存");
        protectOwnMap.clear();
        protectMap.clear();
        ResultSet rs = DatabaseConnector.getPreparedStatement("select * from ProtectArea").executeQuery();
        while(rs.next()){
            String areaId = rs.getString(1);
            String areaRange = rs.getString(2);

        }
    }
}
