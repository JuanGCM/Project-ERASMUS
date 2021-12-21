package cnos.fap.project.repos

import cnos.fap.project.models.User
import cnos.fap.project.models.Work
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WorkRepository: JpaRepository<Work, Long> {

    fun findByUser(user:User):MutableList<Work>

}