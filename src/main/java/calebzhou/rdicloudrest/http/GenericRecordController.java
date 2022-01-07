package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.record.GenericRecord;
import calebzhou.rdicloudrest.model.record.RecordType;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/GenericRecord")
public class GenericRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if(req.getParameter("query")==null)
            SqlUtils.insertObjectToTable(RequestUtils.parseRequstJsonToObject(GenericRecord.class,req),GenericRecord.class);
            else{
                doGet(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<GenericRecord> list=new ArrayList<>();
            ResultSet set = DatabaseConnector.getPreparedStatement(req.getParameter("query").replace("@AST","*")).executeQuery();
            while (set.next()){
                GenericRecord record=new GenericRecord(
                        set.getString(1),
                        RecordType.valueOf(set.getString(2)),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getTimestamp(6)
                );
                list.add(record);
            }
            ResponseUtils.write(resp,new Gson().toJson(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
