package calebzhou.rdi.microservice.dao;

import calebzhou.rdi.microservice.model.Island2;
import calebzhou.rdi.microservice.model.Island2Crew;
import calebzhou.rdi.microservice.model.Island2Loca;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Island2Mapper {
    //岛屿本体 =======
    //根据岛屿id获取岛屿对象
    @Select("select * from Island2 i join Island2Loca il on i.iid=il.iid and i.iid = #{iid} limit 1")
    @Results({
            @Result(
                    id=true,
                    property = "iid",
                    column = "iid"
            ),
            @Result(
                    property = "loca",
                    column = "iid",
                    one = @One(select="getIslandLocationByIid")
            ),
            @Result(
                    property = "crews",
                    column = "iid",
                    javaType = List.class,
                    many=@Many(select="getIslandCrewsByIid")
            )
    })
    Island2 getIslandById(@Param("iid")Integer iid);
    //根据岛屿id获取岛屿所有成员
    @Select("select * from Island2Crew where iid=#{iid}")
    List<Island2Crew> getIslandCrewsByIid(@Param("iid") Integer iid);
    //根据岛屿id获取岛屿的位置
    @Select("select * from Island2Loca where iid=#{iid}")
    Island2Loca getIslandLocationByIid(@Param("iid")Integer iid);
    //玩家是否拥有岛屿
    @Select("SELECT EXISTS(SELECT 1 FROM Island2 WHERE pid = #{pid} LIMIT 1)")
    boolean isPlayerOwnIsland(@Param("pid")String pid);
    //玩家是否加入岛屿
    @Select("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = #{pid} LIMIT 1)")
    boolean isPlayerJoinAnyIsland(@Param("pid")String pid);
    @Select("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = #{pid} and iid=#{iid} LIMIT 1)")
    boolean isPlayerJoinOneIsland(@Param("pid")String pid,@Param("iid")Integer iid);
    //获取玩家拥有的岛屿id
    @Select("select iid from Island2 where pid=#{pid}")
    Integer getPlayerOwnIslandId(@Param("pid")String pid);
    //获取玩家加入的岛屿id
    @Select("select iid from Island2Crew where pid=#{pid}")
    Integer getPlayerJoinIslandId(@Param("pid")String pid);

    @Insert("insert into Island2 (pid,  ts) VALUES (#{pid},#{ts})")
    @Options(useGeneratedKeys = true, keyProperty = "iid")
    void insertIsland(Island2 island);

    @Delete("delete from Island2 where iid=#{iid}")
    void deleteIsland(@Param("iid")Integer iid);

    @Update("update Island2Loca set x=#{x},y=#{y},z=#{z},w=#{w},p=#{p} where iid=#{iid}")
    void updateIslandLocation(@Param("iid")Integer iid,
                              @Param("x")double x,
                              @Param("y")double y,
                              @Param("z")double z,
                              @Param("w")double w,
                              @Param("p")double p
                              );

    @Update("update Island2 set pid=#{newPid} where iid=#{iid}")
    void updateIslandPid(@Param("iid")Integer iid,@Param("newPid")String newPid);
    @Insert("insert into Island2Crew (pid,iid) values (#{mpid},#{iid})")
    void insertMember(@Param("mpid")String mpid,@Param("iid")Integer iid);


    @Delete("delete from Island2Crew where pid=#{mpid} and iid=#{iid}")
    void deleteMember(@Param("mpid")String mpid,@Param("iid")Integer iid);

}
