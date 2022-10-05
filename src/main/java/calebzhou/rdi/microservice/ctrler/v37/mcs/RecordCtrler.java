package calebzhou.rdi.microservice.ctrler.v37.mcs;

import calebzhou.rdi.microservice.component.PassToken;
import calebzhou.rdi.microservice.dao.DatabaseConnector;
import calebzhou.rdi.microservice.dao.RecordMapper;
import calebzhou.rdi.microservice.model.RecordBlock;
import calebzhou.rdi.microservice.utils.RdiSerializer;
import calebzhou.rdi.microservice.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v37/mcs_game/record")
//这个是专门为mc服务端准备的
@PassToken
public class RecordCtrler {
    final RecordMapper mapper;
    public RecordCtrler(RecordMapper mapper) {
        this.mapper = mapper;
    }
    //记录说话、指令
    @PassToken
    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public void recordChat(@RequestParam String pid, @RequestParam String cont) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordChat (pid,cont,ts) values (?,?,?)", pid, cont, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //记录死亡
    @PassToken @RequestMapping(value = "/death", method = RequestMethod.POST)
    public void recordDeath(@RequestParam String pid, @RequestParam String src) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordDeath (pid,src,ts) values (?,?,?)", pid, src, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //记录登入
    @PassToken  @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void recordLogin(@RequestParam String pid, @RequestParam String ip, @RequestParam String geo) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordLogin (pid,ip,geo,ts) values (?,?,?,?)", pid, ip, geo, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //记录UUID和昵称的对应关系（进入服务器时）
    @PassToken @RequestMapping(value = "/idname", method = RequestMethod.POST)
    public void recordIdName(@RequestParam String pid, @RequestParam String name) {
        try (PreparedStatement ps =
                     DatabaseConnector.getPreparedStatement("insert into RecordIdName (pid,pname,ts) values (?,?,?)", pid, name,
                             Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            if(!e.getMessage().equals("EXISTS!"))
                throw new RuntimeException(e);
        }
    }

    //记录登出
    @PassToken @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void recordLogout(@RequestParam String pid) {
        try (PreparedStatement ps = DatabaseConnector.getPreparedStatement("insert into RecordLogout (pid,ts) values (?,?)", pid, Timestamp.valueOf(LocalDateTime.now()))
        ) {
            ps.executeUpdate();
            ps.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //方块破坏/放置
    @PassToken @RequestMapping(value = "/block", method = RequestMethod.POST)
    public void recordBlock(@RequestParam(required=false,defaultValue = "00000000-0000-0000-0000-000000000000") String pid,
                            @RequestParam String bid,
                            @RequestParam int act,
                            @RequestParam String world,
                            @RequestParam int x,
                            @RequestParam int y,
                            @RequestParam int z) {
        //主世界主城以外的地方不记录
        if("minecraft:overworld".equals(world)){
            boolean rangeInSpawn = (x > -256 && x < 256) && (z > -256 && z < 256);
            if(!rangeInSpawn){
                return;
            }
        }
        //末地 地狱不记录
        if("minecraft:the_end".equals(world))
            return;
        if("minecraft:the_nether".equals(world))
            return;

        mapper.insertRecordBlock(new RecordBlock(pid,bid,act,world,x,y,z, TimeUtils.getNow()));

    }

    @PassToken @RequestMapping(value = "/block",method = RequestMethod.GET)
    public String queryBlockRecord(@RequestParam String dim,@RequestParam int x,@RequestParam int y,@RequestParam int z){

        List<RecordBlock> recordBlocks = mapper.getRecordBlocksByCoord(dim, x, y, z);
        return RdiSerializer.GSON.toJson(recordBlocks);
    }


}
