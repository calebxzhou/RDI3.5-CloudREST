package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.model.BlockRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;
import calebzhou.rdicloudrest.utils.SqlUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet("/BlockRecord")
public class BlockRecordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        BlockRecord record = RequestUtils.parseRequstJsonToObject(BlockRecord.class,req);
        try {
                    BlockRecordDao.insertBlockRecord(record);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
