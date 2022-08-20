package calebzhou.rdicloudrest.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class DatabaseConnector {
    public static final String DB_NAME="rdi35";
    public static final String DB_URL = "jdbc:mysql://cdb-p243thok.cd.tencentcdb.com:10083/"+DB_NAME+"?useSSL=true";
    public static final String USR= "root";
    public static final String PWD = "dmts_avia";
    private static final HikariDataSource dataSource;
    private static Connection connection;
    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername(USR);
        config.setPassword(PWD);
        config.setAutoCommit(true);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(600000);
        config.setMaximumPoolSize(500);
        config.setMinimumIdle(20);
        config.setValidationTimeout(3000);
        config.setMaxLifetime(3*60*1000);//3分
        config.setIdleTimeout(60000);
        dataSource= new HikariDataSource(config);
    }
    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();//DriverManager.getConnection(DB_URL,USR,PWD);
    }
    public static PreparedStatement getPreparedStatement(String sql,Object... params) throws  SQLException{
        PreparedStatement ps= getConnection().prepareStatement(sql);
        int paramAmount=params.length;
        for(int i=0;i<paramAmount;i++){
            ps.setObject(i+1,params[i]);
        }
        return ps;
    }
}
