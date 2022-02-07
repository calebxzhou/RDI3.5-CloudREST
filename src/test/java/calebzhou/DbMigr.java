package calebzhou;

import calebzhou.rdicloudrest.dao.BlockRecordDao;
import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.CoordLocation;
import calebzhou.rdicloudrest.model.record.BlockRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMigr {
    public static void main(String[] args) {
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select * from BlockRecord").executeQuery();
            BlockRecord br = new BlockRecord();
            while(rs.next()){
                System.out.println(rs.getTimestamp("oprTime"));
                br.setPid(rs.getString("playerUuid"));
                br.setBlockType(rs.getString("blockType"));
                br.setBlockAction(rs.getString("blockAction"));
                br.setLocation(
                        String.format("%s,%d.0,%d.0,%d.0",
                        rs.getString("dimension"),
                        rs.getInt("posX"),
                        rs.getInt("posY"),
                        rs.getInt("posZ")
                                ));
                br.setRecTime(rs.getTimestamp("oprTime"));
                BlockRecordDao.insertBlockRecord(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
