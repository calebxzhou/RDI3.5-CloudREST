package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.model.record.BlockRecord;
import calebzhou.rdicloudrest.thread.BlockRecordThread;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/BlockRecord")
public class BlockRecordServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        BlockRecord record = requestToObject(BlockRecord.class,req);
        BlockRecordThread.recordQueue.add(record);
        BlockRecordThread.notifyStart();
    }
}
