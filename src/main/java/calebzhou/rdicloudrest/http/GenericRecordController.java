package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.record.GenericRecord;
import calebzhou.rdicloudrest.model.record.RecordType;
import calebzhou.rdicloudrest.dao.GenericDao;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/GenericRecord")
public class GenericRecordController extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getParameter("query") == null)
                GenericDao.insertObjectToTable(requestToObject(GenericRecord.class, req), GenericRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
