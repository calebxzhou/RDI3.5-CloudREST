package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.model.record.UuidNameRecord;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UuidNameRecord")
public class UuidNameRecordController extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);
        UuidNameRecord record = requestToObject(UuidNameRecord.class , req);
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select 1 from UuidNameRecord where pid=? and pname=? limit 1",
                    record.getPlayerUuid(),record.getPname()).executeQuery();
            if(!rs.next()){
                GenericDao.insertObjectToTable(record,UuidNameRecord.class);
                responseSuccess(resp,"success");
            }
            else
                responseError(resp,"exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);

    }
}
