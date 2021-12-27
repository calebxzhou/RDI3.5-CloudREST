package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.model.ChatRecord;
import calebzhou.rdicloudrest.model.LogInOutRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/ChatRecord")
public class ChatRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ChatRecord record= RequestUtils.parseRequstJsonToObject(ChatRecord.class,req);
        try {
            SqlUtils.insertObjectToTable(record,ChatRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
