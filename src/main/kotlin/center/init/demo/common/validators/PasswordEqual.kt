package center.init.demo.common.validators

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class PasswordEqual(
    // 任何自己写的 validator 注解，都必须包含 message groups payload 这三个成员变量
    val message: String = "两次输入的密码不一致",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)