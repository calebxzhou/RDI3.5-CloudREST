package calebzhou;

import calebzhou.rdicloudrest.constants.FileConst;
import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class IslandScoreCalc {
    private static final String[] ignoreBlocks = new String[]{
        "block.minecraft.farmland",
        "block.minecraft.air",
        "block.minecraft.potatoes",
        "block.minecraft.magma_block",
        "block.minecraft.netherrack",
        "block.minecraft.composter",
        "block.minecraft.potatoes",
        "block.minecraft.sand",
        "block.minecraft.gravel",
        "block.minecraft.magma_block",
        "block.minecraft.carrots",
        "block.minecraft.chest",
        "block.minecraft.dirt",
        "block.minecraft.obsidian",
        "block.minecraft.ice",
        "block.minecraft.end_stone",
        "block.minecraft.crafting_table",
        "block.minecraft.bedrock",
        "block.minecraft.piston_head",
        "block.minecraft.lever",
        "block.minecraft.grass_block",
        "block.minecraft.fire",
        "block.minecraft.wheat",
        "block.minecraft.soul_sand",
        "block.minecraft.torch",
        "block.minecraft.sugar_cane"
    };
    static List<String> tables=new ArrayList<>();
    static {
        tables.add("0122");
        tables.add("0123");
        tables.add("0124");
        tables.add("0125");
        tables.add("0126");
        tables.add("0127");
        tables.add("0128");
        tables.add("0129");
        tables.add("0130");
        tables.add("0131");
        tables.add("0201");
        tables.add("0202");
        tables.add("0203");
    }

    private static String getIgnoreBlocksClause(){
        String sqlCond ="";//= "where 1=1";
        for(String ignore:ignoreBlocks){
            sqlCond+=" and blockType<>'"+ignore+"'";
        }
        return sqlCond;
    }
    private static void clearTables() throws SQLException {
       /*
        DatabaseConnector.getPreparedStatement("delete from rdi3.BlockRecord "+sqlCond).executeUpdate();
        DatabaseConnector.getPreparedStatement("delete from rdi3.BlockRecord2 "+sqlCond).executeUpdate();*/
    }
    public static void main(String[] args) throws SQLException, IOException {
        ResultSet rs = DatabaseConnector.getPreparedStatement("select pid,recordType,src,recTime from GenericRecord where recordType = 'login' or recordType = 'logout'").executeQuery();
        while(rs.next()){
            System.out.println(rs.getTimestamp(4));
            DatabaseConnector.getPreparedStatement("insert into LoginRecord values (?,?,?,?)",
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getTimestamp(4)
                    ).executeUpdate();
        }

        /*ResultSet players = connection.prepareStatement("select pname from rdi3.UuidNameRecord").executeQuery();
        List<String> playerList = new ArrayList<>();
        while (players.next()){
            playerList.add(players.getString(1));
        }
        ExecutorService service = Executors.newCachedThreadPool();
        for(String pl:playerList){
                try {
                    calc(connection,pl);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }*/



    }

    private static int calc(Connection connection,String pname) throws SQLException, IOException {
        FileUtils.writeLineToFile(FileConst.scoreFolder,pname,"-----------计算玩家"+pname+"的岛屿积分！-----------");
        AtomicInteger dayScore= new AtomicInteger();




        PreparedStatement ps;ResultSet rs;
        ps = connection.prepareStatement("select count(*) from rdi3.BlockRecord2 br2 inner join rdi3.UuidNameRecord unr on br2.pid=unr.pid and unr.pname=? and br2.blockAction=?"+getIgnoreBlocksClause());
        ps.setString(1,pname);
        ps.setString(2,"PLACE");
        rs = ps.executeQuery();
        rs.next();
        int placed2 = rs.getInt(1);

        ps.setString(2,"BREAK");
        rs=ps.executeQuery();
        rs.next();
        int broken2 = rs.getInt(1);
        FileUtils.writeLineToFile(FileConst.scoreFolder,pname,"20211229-2022-0101 :"+(placed2-broken2)+"分");
        dayScore.addAndGet(placed2 - broken2);


        ps = connection.prepareStatement("select count(*) from rdi3.BlockRecord br2 inner join rdi3.UuidNameRecord unr on br2.playerUuid=unr.pid and unr.pname=? and br2.blockAction=?"+getIgnoreBlocksClause());
        ps.setString(1,pname);
        ps.setString(2,"PLACE");
        rs = ps.executeQuery();
        rs.next();
        int placed3 = rs.getInt(1);

        ps.setString(2,"BREAK");
        rs=ps.executeQuery();
        rs.next();
        int broken3 = rs.getInt(1);
        FileUtils.writeLineToFile(FileConst.scoreFolder,pname,"20220101-2022-0123 :"+(placed3-broken3)+"分");
        dayScore.addAndGet(placed3 - broken3);

        for (String table:tables){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from br.BlockRecord_2022" + table + " brr inner join rdi3.UuidNameRecord UNR on brr.pid = UNR.pid " +
                        "and UNR.pname=? and brr.blockAction=?"+getIgnoreBlocksClause());
                preparedStatement.setString(1,pname);
                preparedStatement.setString(2,"PLACE");
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int placed = resultSet.getInt(1);

                preparedStatement.setString(2,"BREAK");
                resultSet=preparedStatement.executeQuery();
                resultSet.next();
                int broken = resultSet.getInt(1);

                FileUtils.writeLineToFile(FileConst.scoreFolder,pname,String.format("2022%s %s分",table,placed,broken,placed-broken));
                if(placed >= broken)
                    dayScore.addAndGet(placed - broken);



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        FileUtils.writeLineToFile(FileConst.scoreFolder,pname,pname+" 从2021-12-29到2022-02-03，总计岛屿积分："+dayScore.get());
        FileUtils.writeLineToFile(FileConst.scoreFolder,pname,"---------------------------------");
        return dayScore.get();
    }
}
