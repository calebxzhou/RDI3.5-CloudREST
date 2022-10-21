package calebzhou.rdi.microservice.dao

import calebzhou.rdi.microservice.model.entity.*
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-05,8:02.
 */
@Repository
interface IslandMapper {
    //岛屿本体 =======
    //根据岛屿id获取岛屿对象
    @Select("select * from Island2 i join Island2Loca il on i.iid=il.iid and i.iid = #{iid} limit 1")
    @Results(
        Result(id = true, property = "iid", column = "iid"),
        Result(property = "loca", column = "iid", one = One(select = "getIslandLocationByIid")),
        Result(property = "crews", column = "iid", javaType = MutableList::class, many = Many(select = "getIslandCrewsByIid"))
    )
    fun getIslandById(@Param("iid") iid: Int): Island2?

    //根据岛屿id获取岛屿所有成员
    @Select("select * from Island2Crew where iid=#{iid}")
    fun getIslandCrewsByIid(@Param("iid") iid: Int): List<Island2Crew>?

    //根据岛屿id获取岛屿的位置
    @Select("select * from Island2Loca where iid=#{iid}")
    fun getIslandLocationByIid(@Param("iid") iid: Int): Island2Loca

    //玩家是否拥有岛屿
    @Select("SELECT EXISTS(SELECT 1 FROM Island2 WHERE pid = #{pid} LIMIT 1)")
    fun isPlayerOwnIsland(@Param("pid") pid: String): Boolean

    //玩家是否加入岛屿
    @Select("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = #{pid} LIMIT 1)")
    fun isPlayerJoinAnyIsland(@Param("pid") pid: String): Boolean

    @Select("SELECT EXISTS(SELECT 1 FROM Island2Crew WHERE pid = #{pid} and iid=#{iid} LIMIT 1)")
    fun isPlayerJoinOneIsland(@Param("pid") pid: String, @Param("iid") iid: Int): Boolean

    //获取玩家拥有的岛屿id
    @Select("select iid from Island2 where pid=#{pid}")
    fun getPlayerOwnIslandId(@Param("pid") pid: String): Int?

    //获取玩家加入的岛屿id
    @Select("select iid from Island2Crew where pid=#{pid}")
    fun getPlayerJoinIslandId(@Param("pid") pid: String): Int?

    @Insert("insert into Island2 (pid,  ts) VALUES (#{pid},#{ts})")
    //@Options(useGeneratedKeys = true, keyProperty = "iid")
    fun createIsland(pid:String,ts:Timestamp)

    @Delete("delete from Island2 where iid=#{iid}")
    fun deleteIsland(@Param("iid") iid: Int)

    @Update("update Island2Loca set x=#{x},y=#{y},z=#{z},w=#{w},p=#{p} where iid=#{iid}")
    fun updateIslandLocation(
        @Param("iid") iid: Int,
        @Param("x") x: Double,
        @Param("y") y: Double,
        @Param("z") z: Double,
        @Param("w") w: Double,
        @Param("p") p: Double
    )

    @Update("update Island2 set pid=#{newPid} where iid=#{iid}")
    fun updateIslandPid(@Param("iid") iid: Int, @Param("newPid") newPid: String)

    @Insert("insert into Island2Crew (pid,iid) values (#{mpid},#{iid})")
    fun insertMember(@Param("mpid") mpid: String, @Param("iid") iid: Int)


    @Delete("delete from Island2Crew where pid=#{mpid} and iid=#{iid}")
    fun deleteMember(@Param("mpid") mpid: String, @Param("iid") iid: Int)

    @Select(value = ["select concat(x, ',', y, ',', z) from Island where pid=#{pid} limit 1"])
    fun findIsland1IdOwnByPid(@Param("pid") pid: String): String?

    @Select(value = ["select concat(x, ',', y, ',', z) from Island where crew like '%' || #{pid} || '%' limit 1"])
    fun findIsland1IdJoinByPid(@Param("pid") pid: String): String?
}