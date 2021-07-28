package center.init.demo.common.configuration

import center.init.demo.common.hack.AutoPrefixUrlMapping
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping


// 没有打上@Configuration注解，下面的方法也没有打上@Bean注解
// 这里没有用特定的注解，比如@ControllerAdvice
// 就是一个普通的@Component，Spring是怎么知道它的作用的
// 这是Spring的另外一种发现机制，注入容器的Bean通过实现接口，实现了特定的接口
// Spring 就知道这个Bean是用来干嘛的，就会调用下面特定的方法
@Component
class AutoPrefixConfiguration: WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return AutoPrefixUrlMapping()
    }
}