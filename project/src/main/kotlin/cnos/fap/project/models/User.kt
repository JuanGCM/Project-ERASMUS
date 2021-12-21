package cnos.fap.project.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import kotlin.collections.HashSet

@Entity
class User(

    @get:NotBlank(message = "{user.name.blank}")
    var name:String,

    @get:NotBlank(message = "{user.lastname.blank}")
    var lastname:String,

    private var username:String?,

    private var password:String?,

    @get:NotNull(message = "{user.phone.null}")
    var phone:Int,

    @Column(nullable = false, unique = true)
    @get:NotBlank(message="{user.email.blank}")
    @get:Email(message="{user.email.email}")
    var email:String,

    @get:NotBlank(message="{user.address.blank}")
    var address:String,

    @get:NotBlank(message="{user.city.blank}")
    var city:String,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    var companyName:String?= null,

    var companyAddress:String?= null,

    var partitaIVA:String?= null,

    var comments:String?=null,

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    @JsonManagedReference
    var works:MutableList<Work> = mutableListOf(),

    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    private val enabled: Boolean = true,

    private val credentialsNonExpired : Boolean = true,

    @Id @GeneratedValue
    var id:UUID?= null

    ):UserDetails{

    constructor(name: String,lastname:String,username: String?,password: String?, phone:Int,email:String,address:String,
                city:String,rol:String, companyName:String?, companyAddress:String?, partitaIVA:String?,comments:String?):this(
                name,lastname,username, password,phone,email,address,city,mutableSetOf(rol), companyName , companyAddress , partitaIVA ,comments,
                 mutableListOf<Work>(),true,true,true,true)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = nonExpired

    override fun isAccountNonLocked() = nonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled() = enabled

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}