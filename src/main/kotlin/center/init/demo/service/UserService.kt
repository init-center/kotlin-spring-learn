package center.init.demo.service

import center.init.demo.dao.UserDao
import center.init.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService constructor(@Autowired var userDao: UserDao) {
    fun getUser(id: Int): User? {
        return userDao.getUserById(id)
    }
}