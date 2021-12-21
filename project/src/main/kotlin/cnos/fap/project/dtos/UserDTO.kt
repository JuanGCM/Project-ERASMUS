package cnos.fap.project.dtos

import cnos.fap.project.models.User
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserDTO(

    @get:NotBlank(message="{user.name.blank}")
    var name:String,

    @get:NotBlank(message="{user.name.blank}")
    var lastname:String,

    @get:NotBlank(message="{user.username.blank}")
    var username:String?,

    @get:NotNull(message = "{user.phone.null}")
    var phone:Int,

    @get:NotBlank(message="{user.email.blank}")
    @get:Email(message="{user.email.email}")
    var email:String,

    @get:NotBlank(message="{user.address.blank}")
    var address:String,

    @get:NotBlank(message="{user.city.blank}")
    var city:String,

    var companyName:String?,

    var companyAddress:String?,

    var partitaIVA:String?,

    var comments:String?,

    var id: UUID?
)
fun User.toDto(): UserDTO = UserDTO(
    name,
    lastname,
    username,
    phone,
    email,
    address,
    city,
    companyName,
    companyAddress,
    partitaIVA,
    comments,
    id)

data class DtoUserEdit(

    @get:NotBlank(message="{user.name.blank}")
    var name : String,

    @get:NotBlank(message="{user.lastname.blank}")
    var lastname : String,

    @get:NotBlank(message="{user.email.blank}")
    @get:Email(message="{user.email.email}")
    var email : String,

    @get:NotNull(message = "{user.phone.null}")
    var phone: Int,

    @get:NotBlank(message="{user.address.blank}")
    var address:String,

    @get:NotBlank(message="{user.city.blank}")
    var city: String,

    var companyName:String?,

    var companyAddress:String?,

    var partitaIVA:String?,

    var comments:String?
)