package calebzhou.rdicloudrest.dao;

import calebzhou.rdicloudrest.model.RecordBlock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordMapper {
    @Insert("insert into RecordBlock (pid, act, bid, world, x, y, z, ts) VALUES (#{pid},#{act},#{bid},#{world},#{x},#{y},#{z},#{ts})")
    void insertRecordBlock(RecordBlock block);
    @Select("select * from RecordBlock where world=#{dim} and x=#{x} and y=#{y} and z=#{z}")
    List<RecordBlock> getRecordBlocksByCoord(@Param("dim") String dim, @Param("x")int x,@Param("y") int y, @Param("z")int z);
}
