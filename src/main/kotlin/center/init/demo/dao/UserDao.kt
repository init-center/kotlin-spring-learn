package center.init.demo.dao

import center.init.demo.dto.CreateUserDto
import center.init.demo.model.User
import center.init.demo.model.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserDao(@Autowired var database: Database){
    fun getUserById(id: Int): User? {
       return database.users.find { it.id eq id }
    }

    fun createUser(createUserDto: CreateUserDto): Int {
        val user = User{
            name = createUserDto.name
            age = createUserDto.age
        }
        return database.users.add(user)
    }
}