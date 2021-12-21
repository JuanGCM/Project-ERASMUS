package cnos.fap.project.repos

import cnos.fap.project.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {

    fun findByUsername(username : String?) : Optional<User>

    fun findByEmail(email:String?) : User
}