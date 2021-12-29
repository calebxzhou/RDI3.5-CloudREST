package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.record.BlockRecord;
import calebzhou.rdicloudrest.utils.SqlUtils;

import java.sql.SQLException;

public class BlockRecordDao {
    public static int insertBlockRecord(BlockRecord record) throws SQLException, IllegalAccessException {
        return SqlUtils.insertObjectToTable(record, BlockRecord.class);
    }
}
