package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.model.record.GenericRecord;
import calebzhou.rdicloudrest.dao.GenericDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/GenericRecord")
public class GenericRecordController extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
                GenericDao.insertObjectToTable(requestToObject(GenericRecord.class, req), GenericRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
