package calebzhou.rdicloudrest.dao;


import calebzhou.rdicloudrest.model.record.BlockRecord;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static calebzhou.rdicloudrest.dao.DatabaseConnector.BLOCK_RECORD_SCHEMA;

public class BlockRecordDao {

    public static int insertBlockRecord(BlockRecord record) throws SQLException {
        //表 按照日期分割
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String tableName = "br.BlockRecord_"+dateFormat.format(date);
        if(!GenericDao.isTableExists(tableName,BLOCK_RECORD_SCHEMA)){
            DatabaseConnector.getPreparedStatement("""
                    create table %s
                    (
                        pid         char(36)                            not null,
                        blockType   varchar(64)                         not null,
                        blockAction varchar(6)                          null,
                        location    varchar(48)                         not null,
                        recTime     timestamp                           not null 
                    );
                    """.formatted(tableName)).executeUpdate();
        }
        return GenericDao.insertObjectToTable(record, BlockRecord.class,tableName);
    }
}
