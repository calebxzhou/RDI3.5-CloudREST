package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.model.record.BlockRecord;
import calebzhou.rdicloudrest.utils.RequestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
