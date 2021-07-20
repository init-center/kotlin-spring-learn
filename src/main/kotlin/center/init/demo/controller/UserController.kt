package center.init.demo.controller

import center.init.demo.model.User
import center.init.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController constructor(@Autowired var userService: UserService){
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Int): User? {
        return userService.getUser(id)
    }
}