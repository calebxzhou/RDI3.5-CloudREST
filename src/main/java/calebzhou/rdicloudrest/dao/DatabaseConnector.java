package calebzhou.rdicloudrest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    public static final String DB_NAME="rdi3";
    public static final String BLOCK_RECORD_SCHEMA="br";
    public static final String DB_URL = "jdbc:mysql://cdb-p243thok.cd.tencentcdb.com:10083/"+DB_NAME+"?useSSL=true";
    public static final String USR= "root";
    public static final String PWD = "dmts_avia";
    public static final int TIMEOUT = 240;
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Connection connection;
    public static void refreshConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL,USR,PWD);
    }
    public static boolean checkConnection() throws SQLException {
        boolean flag = true;
        if(connection==null)
            refreshConnection();
        if(!connection.isValid(TIMEOUT))
            flag = false;
        if(connection.isClosed())
            flag = false;

        if(!flag)
            refreshConnection();
        return true;
    }
    public static Connection getConnection() throws SQLException {
        checkConnection();
        return connection;
    }
    public static PreparedStatement getPreparedStatement(String sql,Object... params) throws  SQLException{
        PreparedStatement ps=getConnection().prepareStatement(sql);
        int paramAmount=params.length;
        for(int i=0;i<paramAmount;i++){
            ps.setObject(i+1,params[i]);
        }
        return ps;
    }
}
