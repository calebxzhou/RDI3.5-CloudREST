package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.model.LogInOutRecord;
import calebzhou.rdicloudrest.model.PlayerMonitor;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/PlayerMonitor")
public class PlayerMonitorController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PlayerMonitor record= RequestUtils.parseRequstJsonToObject(PlayerMonitor.class,req);
        try {
            SqlUtils.insertObjectToTable(record,PlayerMonitor.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
