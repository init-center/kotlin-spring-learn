package center.init.demo.dto

import center.init.demo.common.validators.PasswordEqual
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank

@PasswordEqual
data class CreateUserDto(
    // 在data class中定义的属性，验证的注解需要加@field:前缀
    // 非空属性必须定义初始值，否则当值为null时反序列化JSON会产生HttpMessageNotReadableException
    @field:NotBlank(message = "名称不能为空") @field:Length(min=2, max=12) val name: String = "",
    @field:Range(min=0,max=120, message = "年龄必须在0 - 120之间") val age: Int = -1,
    @field:NotBlank(message = "密码不能为空") @field:Length(min=8, max=20, message = "密码长度在8 - 20之间") val password: String = "",
    @field:NotBlank(message = "重复密码不能为空") @field:Length(min=8, max=20, message = "重复密码长度在8 - 20之间") val repeatPassword: String = "",
    )