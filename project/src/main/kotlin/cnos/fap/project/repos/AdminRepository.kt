package cnos.fap.project.repos

import cnos.fap.project.models.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AdminRepository : JpaRepository<Admin, UUID> {
}