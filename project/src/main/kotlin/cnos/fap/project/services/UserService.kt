package cnos.fap.project.services

import cnos.fap.project.dtos.DtoUserEdit
import cnos.fap.project.dtos.UserDTO
import cnos.fap.project.dtos.toDto
import cnos.fap.project.errors.ListEntityNotFoundException
import cnos.fap.project.models.User
import cnos.fap.project.repos.UserRepository
import cnos.fap.project.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import cnos.fap.project.errors.SingleEntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwt: JwtTokenProvider

    @Autowired
    lateinit var usRepo:UserRepository

    fun findByUsername(username:String) =
        usRepo.findByUsername(username)

    fun findAll():List<User>{
        var result = usRepo.findAll()
        if(!result.isEmpty()){
            return result
        }else{
            throw ListEntityNotFoundException(User::class.java)
        }
    }

    fun findById(id: UUID)=
        usRepo.findById(id).orElseThrow {
            SingleEntityNotFoundException(
                id.toString(),
                User::class.java
            ) }

    fun findByEmail(email:String) =
        usRepo.findByEmail(email)

    fun create(new:User):Optional<User>{
        if(usRepo.findByUsername(new.username).isPresent)
            return Optional.empty()

        return Optional.of(
            with(new){
                usRepo.save(User(
                    new.name,
                    new.lastname,
                    new.username,
                    encoder.encode(new.password),
                    new.phone,
                    new.email,
                    new.address,
                    new.city,
                    "USER",
                    new.companyName,
                    new.companyAddress,
                    new.partitaIVA,
                    new.comments,
                ))
            }
        )
    }

    fun createUser(newUser: User, token:String): User{
        return usRepo.save(newUser)
    }

    fun modifyUser(token:String, newUser: DtoUserEdit): ResponseEntity<UserDTO> =
        usRepo.findById(jwt.getUserIdFromJWT(token.split(" ").toTypedArray()[1]))
            .map { usEdited ->
                usEdited.name = newUser.name
                usEdited.lastname = newUser.lastname
                usEdited.email = newUser.email
                usEdited.phone = newUser.phone
                usEdited.address = newUser.address
                usEdited.city = newUser.city
                usEdited.companyName = newUser.companyName
                usEdited.companyAddress = newUser.companyAddress
                usEdited.partitaIVA = newUser.partitaIVA
                usEdited.comments = newUser.comments
                ResponseEntity.status(HttpStatus.OK).body(usRepo.save(usEdited).toDto())
            }.orElseThrow {
                SingleEntityNotFoundException(token,User::class.java)
            }

    fun editUser(id: UUID, newUser: User): ResponseEntity<UserDTO> =
        usRepo.findById(id)
            .map { userToModify ->
                userToModify.name = newUser.name
                userToModify.lastname = newUser.lastname
                userToModify.email = newUser.email
                userToModify.phone = newUser.phone
                userToModify.email = newUser.email
                userToModify.address = newUser.address
                userToModify.city = newUser.city
                userToModify.companyName = newUser.companyName
                userToModify.companyAddress = newUser.companyAddress
                userToModify.partitaIVA = newUser.partitaIVA
                userToModify.comments = newUser.comments
                ResponseEntity.status(HttpStatus.OK).body(usRepo.save(userToModify).toDto())
            }.orElseThrow {
                SingleEntityNotFoundException(id.toString(),User::class.java)
            }

    fun deleteUser(id: UUID): ResponseEntity<Any>{
        if(usRepo.existsById(id)){
            usRepo.deleteById(id)
        }
        return ResponseEntity.noContent().build()
    }

}