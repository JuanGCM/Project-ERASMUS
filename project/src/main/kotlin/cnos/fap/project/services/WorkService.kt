package cnos.fap.project.services

import cnos.fap.project.dtos.WorkDTO
import cnos.fap.project.dtos.toDto
import cnos.fap.project.errors.ListEntityNotFoundException
import cnos.fap.project.errors.SingleEntityNotFoundException
import cnos.fap.project.models.User
import cnos.fap.project.models.Work
import cnos.fap.project.repos.UserRepository
import cnos.fap.project.repos.WorkRepository
import cnos.fap.project.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class WorkService {

    @Autowired
    lateinit var workRepo:WorkRepository

    @Autowired
    lateinit var usRepo:UserRepository

    @Autowired
    lateinit var jwt: JwtTokenProvider

    fun findAll():List<Work>{
        var result = workRepo.findAll()
        if(result.isNotEmpty()){
            return result
        }else{
            throw ListEntityNotFoundException(Work::class.java)
        }
    }

    fun findAllByUserEmail(email:String):MutableList<Work>{
        var result = workRepo.findByUser(usRepo.findByEmail(email))
        if(result.isNotEmpty()){
            return result
        }else{
            throw ListEntityNotFoundException(Work::class.java)
        }
    }

    fun findById(id:Long):Work =
        workRepo.findById(id).orElseThrow {
            SingleEntityNotFoundException(id.toString(),Work::class.java)
        }

    //Creación del newWork que pasa por parametro un objeto Work ,
    // el email del cliente y el token del admin
    fun createWork(newWork: Work,email:String, token:String): Work{

        //Hace busqueda del user admin por token por seguridad
        var segur = usRepo.findById(jwt.getUserIdFromJWT(token.split(" ")
            .toTypedArray()[1])).orElseThrow {
            SingleEntityNotFoundException(jwt.getUserIdFromJWT(token.split(" ")
                .toTypedArray()[1]).toString(), User::class.java)
        }
        //Obtenemos el cliente
        var user = usRepo.findByEmail(email)
        //Creamos el Work y lo guardamos
        var work = Work(newWork.serviceType,newWork.printMaterial,newWork.printNumber,
            newWork.printColor,newWork.time,newWork.price,newWork.date, user, newWork.designWidth,
            newWork.designHeight,newWork.designText,newWork.printSize,newWork.printWidth,newWork.printHeight)
        //guardamos el work
        workRepo.save(work)
        //limpiamos el listado de works en la tabla usuario
        user.works.clear()
        //los volvemos a rellenar con todos los works encontrados por medio del usuario
        user.works = (workRepo.findByUser(user))
        //guardamos el usuario
        usRepo.save(user)

        return work
    }

    fun editWork(id:Long, token:String, newWork: Work): ResponseEntity<WorkDTO> =
        workRepo.findById(id)
            .map { workEdited ->
                workEdited.serviceType = newWork.serviceType
                workEdited.printMaterial = newWork.printMaterial
                workEdited.printNumber = newWork.printNumber
                workEdited.printColor = newWork.printColor
                workEdited.time = newWork.time
                workEdited.price = newWork.price
                workEdited.date = newWork.date
                workEdited.designWidth = newWork.designWidth
                workEdited.designHeight = newWork.designHeight
                workEdited.designText = newWork.designText
                workEdited.printSize = newWork.printSize
                workEdited.printWidth = newWork.printWidth
                workEdited.printHeight = newWork.printHeight
                ResponseEntity.status(HttpStatus.OK).body(workRepo.save(workEdited).toDto())
            }.orElseThrow {
                SingleEntityNotFoundException(token,Work::class.java)
            }

    fun deleteWork(id: Long): ResponseEntity<Any>{
        if(workRepo.existsById(id)){
            workRepo.deleteById(id)
        }
        return ResponseEntity.noContent().build()
    }
}