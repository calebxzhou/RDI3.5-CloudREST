package calebzhou.rdicloudrest.dao;

import java.sql.SQLException;

public interface Cacheable {
    void initCache() throws SQLException;
}
