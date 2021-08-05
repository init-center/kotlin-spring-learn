package center.init.demo.common.security

// 使用了 kotlin DSL 来编写 security config 必须引入这个
// 现在的kotlin和idea有点问题，不会自动引入，也不会提示引入，需要自己手动引入
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// 使用@EnableWebSecurity来注解配置类
// 可以接收debug开启调试输出
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 这种方式和在配置文件中编写用户和密码类似
        auth.inMemoryAuthentication()
            .withUser("user")
            .password(passwordEncoder().encode("123456"))
            .roles("ADMIN")
            .and()
            .withUser("hi")
            .password("12345")
            .roles("USER")
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(web: WebSecurity) {
        // 让 public 下的请求不进入过滤链
        // 这通常对于静态资源非常有用
        web.ignoring().antMatchers("/public/**")
    }

    override fun configure(http: HttpSecurity?) {
        // Kotlin DSL 写法
        http {
            authorizeRequests {

                // 按照从上往下的顺序来匹配，一旦匹配停止匹配
                // 所以拦截规则的顺序不能写错。

                // css 下的所有请求都可以通过
                authorize("/css/**", permitAll)
                // 满足 ADMIN role 可以通过
                authorize("/admin/**", hasAuthority("ADMIN"))
                // v1 下的所有请求需要认证
                authorize("/v1/**", authenticated)
                // 所有请求都需要认证
                //authorize(anyRequest, authenticated)
            }

            formLogin {
                // 指定登录路由
                //loginPage = "/login"
                // 登录接口不要被拦截
                permitAll()
                authenticationSuccessHandler = AuthSuccessHandler()
                authenticationFailureHandler = AuthFailHandler()
            }

            httpBasic{

            }
            csrf{
                disable()
            }

            rememberMe {
                // 指定过期时间
                tokenValiditySeconds = 30 * 24 * 3600
                // 指定 rememberMe 的 cookie 的 name
                rememberMeCookieName = "rememberMeCookieName"
            }
        }

        // 传统写法，这种写法也是可以的，and()方法用来连接多个配置项
//        http.authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login.html")
//            .permitAll()
//            .and()
//            .csrf().disable();
    }

    // 认证成功处理函数
    private class AuthSuccessHandler: AuthenticationSuccessHandler {
        override fun onAuthenticationSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication
        ) {
            response.contentType = "application/json;charset=utf-8"
            response.writer.println(ObjectMapper().writeValueAsString(authentication))
        }

    }

    // 认证失败处理函数
    private class AuthFailHandler: AuthenticationFailureHandler {
        override fun onAuthenticationFailure(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exception: AuthenticationException
        ) {
            response.contentType = "application/json;charset=utf-8"
            response.writer.println(ObjectMapper().writeValueAsString(exception))
        }

    }
}
