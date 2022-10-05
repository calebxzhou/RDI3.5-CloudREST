package calebzhou.rdi.microservice.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

/**
 * Created by calebzhou on 2022-10-05,7:32.
 */
@Configuration
@MapperScan("calebzhou.rdi.microservice.dao")
class MybatisConfig {
    /*@Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(DatabaseConnector.getHikariDataSource())
        return factoryBean.getObject()
    }*/
}