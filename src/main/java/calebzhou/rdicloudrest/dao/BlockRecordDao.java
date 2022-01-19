package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.record.BlockRecord;

import java.sql.SQLException;

public class BlockRecordDao {
    public static int insertBlockRecord(BlockRecord record) throws SQLException, IllegalAccessException {
        return GenericDao.insertObjectToTable(record, BlockRecord.class);
    }
}
