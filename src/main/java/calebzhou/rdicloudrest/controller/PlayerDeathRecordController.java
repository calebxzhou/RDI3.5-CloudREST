package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.model.record.PlayerDeathRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/PlayerDeathRecord")
public class PlayerDeathRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PlayerDeathRecord record= RequestUtils.parseRequstJsonToObject(PlayerDeathRecord.class,req);
        try {
            SqlUtils.insertObjectToTable(record,PlayerDeathRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
