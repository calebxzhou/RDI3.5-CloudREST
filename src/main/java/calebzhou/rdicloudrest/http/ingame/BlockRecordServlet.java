package calebzhou.rdicloudrest.http.ingame;

import calebzhou.rdicloudrest.http.BasicServlet;
import calebzhou.rdicloudrest.model.record.BlockRecord;
import calebzhou.rdicloudrest.thread.BlockRecordThread;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BlockRecord")
public class BlockRecordServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        BlockRecord record = requestToObject(BlockRecord.class,req);
        BlockRecordThread.recordQueue.add(record);
        BlockRecordThread.notifyStart();
    }
}
