package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.model.record.GenericRecord;
import calebzhou.rdicloudrest.dao.GenericDao;
import calebzhou.rdicloudrest.model.record.RecordType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/GenericRecord")
public class GenericRecordServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        GenericRecord record = requestToObject(GenericRecord.class, req);
        RecordType type = record.getRecordType();
        try {
            if(type==RecordType.chat){
                DatabaseConnector.getPreparedStatement("insert into ChatRecord values (?,?,?)",
                        record.getPid(),record.getContent(),record.getRecTime()).executeUpdate();
            }else if(type==RecordType.death){
                DatabaseConnector.getPreparedStatement("insert into DeathRecord values (?,?,?)",
                        record.getPid(),record.getSrc(),record.getRecTime()).executeUpdate();
            }else if(type==RecordType.login||type==RecordType.logout){
                DatabaseConnector.getPreparedStatement("insert into LoginRecord values (?,?,?,?)",
                        record.getPid(),type.toString(),record.getSrc(),record.getRecTime()).executeUpdate();
            }
            else{
                GenericDao.insertObjectToTable(record, GenericRecord.class);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
