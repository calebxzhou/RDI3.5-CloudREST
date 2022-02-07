package calebzhou;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.record.BlockRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbMigr2 {
    public static void main(String[] args) {
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select pid,content,recTime from GenericRecord where recordType='chat'").executeQuery();
            while(rs.next()){
                System.out.println(rs.getTimestamp("recTime"));
                DatabaseConnector.getPreparedStatement("insert into ChatRecord values (?,?,?)",
                        rs.getString("pid"),
                        rs.getString("content"),
                        rs.getTimestamp("recTime")
                ).executeUpdate();
                DatabaseConnector.getPreparedStatement("delete from GenericRecord where pid=? and content=? and recTime=?",
                        rs.getString("pid"),
                        rs.getString("content"),
                        rs.getTimestamp("recTime")
                ).executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
