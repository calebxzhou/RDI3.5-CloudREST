package calebzhou.rdi.microservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ChannelSecurityConfigurer.ChannelRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.web.SecurityFilterChain

/**
 * Created by calebzhou on 2022-10-05,7:40.
 */
@Configuration
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .requiresChannel { channel ->
                channel.anyRequest().requiresSecure()
            }
            .authorizeRequests { authorize ->
                authorize.anyRequest().permitAll()
            }
            .csrf().disable()
            .build()
    }
}