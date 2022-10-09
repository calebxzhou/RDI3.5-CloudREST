package calebzhou.rdi.microservice.dao

import calebzhou.rdi.microservice.model.Account
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

/**
 * Created by calebzhou on 2022-09-09,7:35.
 */
@Repository
interface AccountMapper {
    @Select("select exists(select 1 from Account where id=#{id} limit 1)")
    fun isIdAlreadyRegistered(@Param("id") id: String): Boolean

    @Insert("insert into Account values (#{id},#{pwd},#{regIp},#{regTime})")
    fun insertAccount(account: Account)

    @Select("select exists(select 1 from Account where id=#{id} and pwd=#{pwd})")
    fun isIdMatchesPwd(id: String?, pwd: String?): Boolean

}