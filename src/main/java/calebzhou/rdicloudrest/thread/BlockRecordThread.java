package calebzhou.rdicloudrest.thread;

import calebzhou.rdicloudrest.ThreadPool;
import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.model.record.BlockRecord;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
@Slf4j
public class BlockRecordThread extends Thread{
    public static Queue<BlockRecord> recordQueue = new LinkedList<>();
    public static void notifyStart(){
        ThreadPool.newThread(()->new BlockRecordThread().run());
    }
    public BlockRecordThread() {
    }

    @Override
    public void run() {
        BlockRecord record = recordQueue.poll();
        log.info(recordQueue.size()+" 方块记录上传队列内");
        if(record!=null){
            try {
                BlockRecordDao.insertBlockRecord(record);
                log.info(recordQueue.size()+" 方块记录上传队列内");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            run();
        }


    }
}
