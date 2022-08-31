package calebzhou.rdi.microservice.config;


import calebzhou.rdi.microservice.dao.DatabaseConnector;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("calebzhou.rdi.microservice.dao")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(DatabaseConnector.getHikariDataSource());
        return factoryBean.getObject();
    }
}
