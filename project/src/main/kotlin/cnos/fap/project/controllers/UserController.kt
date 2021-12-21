package cnos.fap.project.controllers

import cnos.fap.project.dtos.DtoUserEdit
import cnos.fap.project.dtos.UserDTO
import cnos.fap.project.dtos.toDto
import cnos.fap.project.models.User
import cnos.fap.project.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController (
    val userService:UserService
){
    @GetMapping()
    fun getUsers()=userService.findAll().map { it.toDto() }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id:UUID)= userService.findById(id).toDto()

    @GetMapping("/getUser/{email}")
    fun getUserByEmail(@PathVariable email:String)= userService.findByEmail(email)


    @PostMapping("/")
    fun newUser(@Valid @RequestBody newUser:User):ResponseEntity<UserDTO> =
        userService.create(newUser).map { ResponseEntity.status(
            HttpStatus.CREATED
        ).body(it.toDto()) }.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST,
                "This username ${newUser.username} already exists"
            ) }

    @PostMapping("/newUser/")
    fun createUser(@Valid @RequestBody newUser: User,
                         @RequestHeader("Authorization") token:String) : ResponseEntity<UserDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser,token).toDto())
    }

    @PutMapping()
    fun modifyUser(@RequestHeader("Authorization") token:String,@Valid @RequestBody newUser: DtoUserEdit)=
        userService.modifyUser(token, newUser)

    @PutMapping("/editUser/{id}")
    fun editUser(@PathVariable id: UUID,@Valid @RequestBody newUser: User)=
        userService.editUser(id, newUser)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID) =
        userService.deleteUser(id)
}