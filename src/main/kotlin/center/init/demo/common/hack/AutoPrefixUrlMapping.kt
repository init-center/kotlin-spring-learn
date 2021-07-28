package center.init.demo.common.hack

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

class AutoPrefixUrlMapping: RequestMappingHandlerMapping() {
    // 使用@Value拿到配置中指定的存放controller的包名
    @Value("\${demo.controller-package}")
    lateinit var controllerPackagePath: String

    // 重写方法
    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        // 先调用我们自己写的getPrefix处理好路由路径
        // RequestMappingInfo有一个静态方法paths，将处理好的路由路径传入，返回一个Builder
        // 调用builder的build方法返回新的RequestMappingInfo，比如控制器所在的包名center.init.demo.controller.v1
        // 我们处理成了/v1
        // 调用父类的原方法，可以拿到本来的RequestMappingInfo，也就是RequestMapping的参数对应的路径，比如传入/user
        // 我们使用处理好的RequestMappingInfo，上面有combine方法，可以连接其它的RequestMappingInfo
        // 连接得到/v1/user的RequestMappingInfo
        // 返回处理后的新路由
        return super.getMappingForMethod(method, handlerType)
            ?.let { RequestMappingInfo.paths(this.getPrefix(handlerType)).build().combine(it) }
    }

    private fun getPrefix(handlerType: Class<*>): String {
        // handlerType上有packageName可以拿到被RequestMapping注解的控制器的包名，我们直接将控制器所在的路径替换为空然后再替换.为/
        return handlerType.packageName.replace(this.controllerPackagePath, "").replace(".", "/")
    }
}