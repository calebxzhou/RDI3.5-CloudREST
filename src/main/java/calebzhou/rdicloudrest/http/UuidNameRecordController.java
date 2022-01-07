package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.record.UuidNameRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/UuidNameRecord")
public class UuidNameRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        if(query != null){
            try {
                List<UuidNameRecord> records = SqlUtils.queryAll(UuidNameRecord.class);
                ResponseUtils.write(resp , new Gson().toJson(records));
            } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        }
        UuidNameRecord record = RequestUtils.parseRequstJsonToObject(UuidNameRecord.class , req);
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select 1 from UuidNameRecord where pid=? and pname=? limit 1",
                    record.getPlayerUuid(),record.getPname()).executeQuery();
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
