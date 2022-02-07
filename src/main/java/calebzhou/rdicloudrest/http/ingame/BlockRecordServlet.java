package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.model.record.BlockRecord;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/BlockRecord")
public class BlockRecordServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        BlockRecord record = requestToObject(BlockRecord.class,req);
        try {
            BlockRecordDao.insertBlockRecord(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
