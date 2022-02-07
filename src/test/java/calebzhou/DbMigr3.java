package calebzhou;

import calebzhou.rdicloudrest.dao.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMigr3 {
    public static void main(String[] args) {
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select * from LoginRecord where act='login' or act='logout'").executeQuery();
            while(rs.next()){
                System.out.println(rs.getTimestamp("recTime"));
                try {
                    DatabaseConnector.getPreparedStatement("delete from GenericRecord where pid=? and recordType=? and src=? and recTime=?",
                            rs.getString("pid"),
                            rs.getString("act"),
                            rs.getString("ip"),
                            rs.getTimestamp("recTime")
                    ).executeUpdate();
                } catch (SQLException e) {
                    System.out.println("no record/"+e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
