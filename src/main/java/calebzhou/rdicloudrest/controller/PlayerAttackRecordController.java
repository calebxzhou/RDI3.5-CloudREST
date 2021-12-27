package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.model.LogInOutRecord;
import calebzhou.rdicloudrest.model.PlayerAttackRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/PlayerAttackRecord")
public class PlayerAttackRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PlayerAttackRecord record= RequestUtils.parseRequstJsonToObject(PlayerAttackRecord.class,req);
        try {
            SqlUtils.insertObjectToTable(record,PlayerAttackRecord.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
