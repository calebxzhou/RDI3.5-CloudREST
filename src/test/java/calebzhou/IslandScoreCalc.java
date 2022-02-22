package calebzhou;

import calebzhou.rdicloudrest.constants.FileConst;
import calebzhou.rdicloudrest.dao.DatabaseConnector;
import calebzhou.rdicloudrest.model.record.BlockRecord;
import calebzhou.rdicloudrest.utils.FileUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IslandScoreCalc {
    private static final List<String> ignoreBlocks = Arrays.asList("block.minecraft.farmland",
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
            "block.minecraft.sugar_cane");
    public static final List<String> bonusBlockPrefix = Arrays.asList("block.modern_industrialization","block.ae2","block.expandedstorage","block.techreborn"
    ,"block.botania","block.skyutils","block.indrev");


    //日期，从20211229开始（表名）
    final String startDate = "20211229";
    final DateTimeFormatter tableNameFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    //uuid  name
    Map<String,String> uuidNameMap;
    private void startCalc(){
        ConcurrentHashMap<String,Integer> uuidScoreMap = new ConcurrentHashMap<>();
        ExecutorService taskExecutor = Executors.newFixedThreadPool(24);
        //20211229到现在,for循环
        LocalDate tableNameDate;
        //玩家列表作为线程数量
        for(tableNameDate = LocalDate.parse(startDate, tableNameFormat);
                tableNameDate.isBefore(LocalDate.now());
                tableNameDate=tableNameDate.plusDays(1))
        {
            final LocalDate finalTableNameDate = tableNameDate;
            taskExecutor.execute(()->{
                System.out.println("\n"+finalTableNameDate+"\n");
                final Multimap<String,BlockRecord> recordOfDay = getRecordOfDay(finalTableNameDate);
                uuidNameMap.entrySet().parallelStream().forEach(entry -> {

                    String pid = entry.getKey();
                    String pname = entry.getValue();
                    Collection<BlockRecord> blockRecords = recordOfDay.get(pid);
                    AtomicInteger score= new AtomicInteger();
                    blockRecords.parallelStream().forEach(br->{
                        bonusBlockPrefix.parallelStream().forEach(p->{
                            if(br.getBlockType().startsWith(p))
                                score.addAndGet(3);
                            else
                                score.addAndGet(1);
                        });
                    });
                    if(score.get()>0)
                        System.out.print(pname+"="+score.get()+";   ");
                    uuidScoreMap.put(pid,uuidScoreMap.get(pid)==null?score.get():uuidScoreMap.get(pid)+score.get());
                });
            });

        }
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------\n");
        System.out.println(uuidScoreMap);
        FileUtils.writeToFile(new File(FileConst.loginLogFolder, "info.txt"),uuidScoreMap.toString());
        try {
            DatabaseConnector.getPreparedStatement("delete * from rdi3.IslandScore").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        uuidScoreMap.forEach((pid,score)->{
            try {
                DatabaseConnector.getPreparedStatement("insert into rdi3.IslandScore values (?,?)",pid,score).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
    private Map<String,String> getUuidNameMap(){
        Map<String,String> unmap = new HashMap<>();
        ResultSet rs = null;
        try {
            rs = DatabaseConnector.getPreparedStatement("select * from rdi3.UuidNameRecord").executeQuery();

            while(rs.next()){
                unmap.put(rs.getString("pid"),rs.getString("pname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unmap;
    }
    private Multimap<String,BlockRecord> getRecordOfDay(LocalDate date){
        String tableName="br.BlockRecord_"+date.format(tableNameFormat);

        Multimap<String,BlockRecord> map = LinkedHashMultimap.create();
        try {
            ResultSet rs = DatabaseConnector.getPreparedStatement("select * from " + tableName).executeQuery();
            String pid="";
            String blockType="";
            String blockAction="";
            while(rs.next()){
                pid= rs.getString("pid");
                blockType= rs.getString("blockType");
                blockAction= rs.getString("blockAction");
                if(ignoreBlocks.contains(blockType))
                    continue;
                map.put(pid,new BlockRecord(null,blockType,blockAction,null));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*private static String getIgnoreBlocksClause(){
        String sqlCond ="";//= "where 1=1";
        for(String ignore:ignoreBlocks){
            sqlCond+=" and blockType<>'"+ignore+"'";
        }
        return sqlCond;
    }*/

    public static void main(String[] args) throws SQLException, IOException {
        IslandScoreCalc calc = new IslandScoreCalc();
        calc.uuidNameMap = calc.getUuidNameMap();
        calc.startCalc();


    }

    private static void calc(Connection connection,String pname) throws SQLException, IOException {
       /* FileUtils.writeLineToFile(FileConst.scoreFolder,pname,"-----------计算玩家"+pname+"的岛屿积分！-----------");
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
        return dayScore.get();*/
    }
}
