package calebzhou.rdi.microservice.dao

import calebzhou.rdi.microservice.model.*
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

/**
 * Created by calebzhou on 2022-10-05,22:10.
 */

@Repository
interface RecordMapper {
    @Insert("insert into RecordChat (pid, cont, ts) VALUES (#{pid},#{cont},#{ts})")
    fun insertRecordChat(chat: RecordChat)
    @Insert("insert into RecordDeath (pid, src, ts) VALUES (#{pid},#{src},#{ts})")
    fun insertRecordDeath(death: RecordDeath)
    @Insert("insert into RecordLogin (pid, ip,geo, ts) VALUES (#{pid},#{ip},#{geo},#{ts})")
    fun insertRecordLogin(login: RecordLogin)
    @Insert("insert into RecordIdName (pid, pname, ts) VALUES (#{pid},#{pname},#{ts})")
    fun insertRecordIdName(idName: RecordIdName)
    @Insert("insert into RecordLogout (pid, ts) VALUES (#{pid}, #{ts})")
    fun insertRecordLogout(logout: RecordLogout)

    @Insert("insert into RecordBlock (pid, act, bid, world, x, y, z, ts) VALUES (#{pid},#{act},#{bid},#{world},#{x},#{y},#{z},#{ts})")
    fun insertRecordBlock(block: RecordBlock)

    @Select("select * from RecordBlock where world=#{dim} and x=#{x} and y=#{y} and z=#{z}")
    fun getRecordBlocksByCoord(
        @Param("dim") dim: String,
        @Param("x") x: Int,
        @Param("y") y: Int,
        @Param("z") z: Int
    ): List<RecordBlock>?

}