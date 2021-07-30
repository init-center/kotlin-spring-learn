package center.init.demo.controller.v1

import center.init.demo.common.exception.exceptions.FailHttpException
import center.init.demo.common.exception.exceptions.NotFoundHttpException
import center.init.demo.common.response.Response
import center.init.demo.dto.CreateUserDto
import center.init.demo.model.User
import center.init.demo.service.UserService
import org.hibernate.validator.constraints.Range
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min


@RestController
@RequestMapping("/user")
@Validated
class UserController constructor(@Autowired var userService: UserService, @Autowired var request: HttpServletRequest){

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(
        @PathVariable("id")
        @Min(value=1, message="ID必须大于1") id: Int,
        @RequestParam @Range(min=0, max= 120, message="年龄必须在0 - 120之间") age: Int?
    ): Response<User> {
        println(age)
        val user = userService.getUser(id)

        if (user === null) {
            throw NotFoundHttpException("用户不存在")
        }
        return Response(2000, "成功", request.method, request.requestURI, user)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser( @RequestBody @Validated user: CreateUserDto): Response<Nothing?> {
        val i = userService.createUser(user)

        if (i < 1) {
            throw FailHttpException("创建用户失败")
        }
        return Response(2000, "成功", request.method, request.requestURI, null)
    }
}