package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.UuidNameRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UuidNameRecord")
public class UuidNameRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UuidNameRecord record = RequestUtils.parseRequstJsonToObject(UuidNameRecord.class , req);
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select * from UuidNameRecord where playerUuid=? and playerName=?",
                    record.getPlayerUuid(),record.getPlayerName()).executeQuery();
            if(!rs.next()){
                SqlUtils.insertObjectToTable(record,UuidNameRecord.class);
                ResponseUtils.write(resp,"success");
            }
            else
                ResponseUtils.write(resp,"exists");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
