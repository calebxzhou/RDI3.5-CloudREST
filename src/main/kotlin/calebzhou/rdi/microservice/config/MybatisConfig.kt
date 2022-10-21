package calebzhou.rdi.microservice.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

/**
 * Created by calebzhou on 2022-10-05,7:32.
 */

@Configuration
@MapperScan("calebzhou.rdi.microservice.dao")
class MybatisConfig {
    private val config = HikariConfig()
    private lateinit var ds: HikariDataSource

    init
    {
        config.jdbcUrl = "jdbc:mysql://cdb-p243thok.cd.tencentcdb.com:10083/rdi35?useSSL=true"
        config.username = "root"
        config.password = "dmts_avia"
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        ds =  HikariDataSource( config)
    }
    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(ds)
        return factoryBean.getObject()
    }
}