package center.init.demo.common.validators

import center.init.demo.dto.CreateUserDto
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordValidator : ConstraintValidator<PasswordEqual, CreateUserDto> {
    // 重写isValid方法用于验证，返回false验证失败，true则成功
    override fun isValid(createUserDto: CreateUserDto, context: ConstraintValidatorContext): Boolean {
        val password = createUserDto.password
        val repeatPassword = createUserDto.repeatPassword
        return password == repeatPassword
    }

    override fun initialize(constraintAnnotation: PasswordEqual) {
        super.initialize(constraintAnnotation)
        // 使用 constraintAnnotation可以获取绑定的注解的参数
        // 比如要获取message
        //constraintAnnotation.message
        // 要让isValid也可以拿到参数，那么就在这个类里面定义属性
        // 然后在这里拿到赋值给类的属性
        // this.message = constraintAnnotation.message
    }
}