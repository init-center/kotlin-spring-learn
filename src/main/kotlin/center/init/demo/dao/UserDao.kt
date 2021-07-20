package center.init.demo.dao

import center.init.demo.model.User
import org.ktorm.database.Database
import center.init.demo.model.users
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserDao(@Autowired var database: Database){
    fun getUserById(id: Int): User? {
       return database.users.find { it.id eq id }
    }
}