package center.init.demo.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface User : Entity<User> {
    companion object : Entity.Factory<User>()

    /**
     * User ID.
     */
    val id: Int

    /**
     * User name.
     */
    var name: String

    /**
     * User age
     */
    var age: Int
}

object Users : Table<User>("user") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val age = int("age").bindTo { it.age }
}

/**
 * Return a default entity sequence of [Users].
 */
val Database.users get() = this.sequenceOf(Users)