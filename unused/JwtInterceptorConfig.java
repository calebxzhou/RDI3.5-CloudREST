package calebzhou.rdi.microservice.component;

/**
 * Created by calebzhou on 2022-09-09,21:54.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lehr
 */
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //先不开启jwt验证 以后再说
        //默认拦截所有路径
        registry.addInterceptor(authenticationInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }
    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }
}
