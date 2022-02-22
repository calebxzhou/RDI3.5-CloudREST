package calebzhou.rdicloudrest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class DatabaseConnector {

    public static final String DB_NAME="rdi3";
    public static final String BLOCK_RECORD_SCHEMA="br";
    public static final String DB_URL = "jdbc:mysql://cdb-p243thok.cd.tencentcdb.com:10083/"+DB_NAME+"?useSSL=true";
    public static final String USR= "root";
    public static final String PWD = "dmts_avia";
    public static final int TIMEOUT = 240;
    //连接数
    public static final int CONN_MAX_AMOUNT = 24;
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static final List<Connection> connections = new ArrayList<>();
    public static void refreshConnection() throws SQLException {
        connections.forEach(connection-> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        connections.clear();
        for (int i = 0; i < CONN_MAX_AMOUNT; i++) {
            connections.add(DriverManager.getConnection(DB_URL,USR,PWD));
        }
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
    }
    public static Connection getConnection(int num) throws SQLException{
        checkConnection(num);
        return connections.get(num);
    }
    public static Connection getRandomConnection() throws SQLException {
        int ran = new SplittableRandom().nextInt(0, CONN_MAX_AMOUNT) ;

        return getConnection(ran);
    }
    public static PreparedStatement getPreparedStatement(String sql,Object... params) throws  SQLException{
        PreparedStatement ps= getRandomConnection().prepareStatement(sql);
        int paramAmount=params.length;
        for(int i=0;i<paramAmount;i++){
            ps.setObject(i+1,params[i]);
        }
        return ps;
    }
}
