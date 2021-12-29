package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.model.record.LogInOutRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/LogInOutRecord")
public class LogInOutRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LogInOutRecord record= RequestUtils.parseRequstJsonToObject(LogInOutRecord.class,req);
        try {
            SqlUtils.insertObjectToTable(record,LogInOutRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
