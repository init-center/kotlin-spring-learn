package center.init.demo.controller.v1

import center.init.demo.common.exception.exceptions.NotFoundHttpException
import center.init.demo.common.response.Response
import center.init.demo.model.User
import center.init.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest




@RestController
@RequestMapping("/user")
class UserController constructor(@Autowired var userService: UserService, @Autowired var request: HttpServletRequest){

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Int): Response<User> {
        val user = userService.getUser(id)
        if (user === null) {
            throw NotFoundHttpException("用户不存在")
        }
        return Response<User>(2000, "成功", request.method, request.requestURI, user)
    }
}