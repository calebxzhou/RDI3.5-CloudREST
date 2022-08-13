package calebzhou.rdicloudrest.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.SplittableRandom;

@Slf4j
public class DatabaseConnector {
static SplittableRandom rand = new SplittableRandom();
    public static final String DB_NAME="rdi35";
    public static final String BLOCK_RECORD_SCHEMA="br";
    public static final String DB_URL = "jdbc:mysql://cdb-p243thok.cd.tencentcdb.com:10083/"+DB_NAME+"?useSSL=true";
    public static final String USR= "root";
    public static final String PWD = "dmts_avia";
    public static final int TIMEOUT = 240;
    //连接数
    public static final int CONN_MAX_AMOUNT = 10;
    private static final HikariDataSource dataSource;
    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername(USR);
        config.setPassword(PWD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource= new HikariDataSource(config);
    }
   /* private static final List<Connection> connections = new ArrayList<>();
    public static void refreshConnection() throws SQLException {
        ExecutorService executor = Executors.newFixedThreadPool(CONN_MAX_AMOUNT);
        connections.forEach(connection-> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        connections.clear();
        for (int i = 0; i < CONN_MAX_AMOUNT; i++) {
            int finalI = i;
           // executor.execute(()->{
                log.info("启动连接 {}", finalI);
                try {
                    connections.add(DriverManager.getConnection(DB_URL,USR,PWD));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
           // });

        }
        *//*try {
            executor.shutdown();
            executor.awaitTermination(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*//*
    }
    public static boolean checkConnection(int num) throws SQLException {
        boolean flag = true;
        if(connections.size()==0){
            refreshConnection();
            return true;
        }

        Connection conn  = connections.get(num);
        if(!conn.isValid(TIMEOUT))
            flag = false;
        if(conn.isClosed())
            flag = false;

        if(!flag)
            refreshConnection();

        return true;
    }*/
    public static Connection getConnection() throws SQLException{
        /*checkConnection(num);
        return connections.get(num);*/
        return dataSource.getConnection();
    }
    /*public static Connection getRandomConnection() throws SQLException {
        int ran = rand.nextInt(0, CONN_MAX_AMOUNT) ;
        log.info("获取连接 {}",ran);
        return getConnection(ran);
    }*/
    public static PreparedStatement getPreparedStatement(String sql,Object... params) throws  SQLException{
        PreparedStatement ps= getConnection().prepareStatement(sql);
        int paramAmount=params.length;
        for(int i=0;i<paramAmount;i++){
            ps.setObject(i+1,params[i]);
        }
        return ps;
    }
}
