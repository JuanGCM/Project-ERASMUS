package cnos.fap.project.controllers

import cnos.fap.project.dtos.WorkDTO
import cnos.fap.project.dtos.toDto
import cnos.fap.project.models.Work
import cnos.fap.project.services.WorkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/works")
class WorkController(
    val workService: WorkService
) {

    @GetMapping()
    fun getWorks()=workService.findAll().map { it.toDto() }

    @GetMapping("/userWorks/{email}")
    fun getWorksByUserEmail(@PathVariable email:String)=workService.findAllByUserEmail(email).map { it.toDto() }

    @GetMapping("/{id}")
    fun getWorkById(@PathVariable id:Long)= workService.findById(id).toDto()

    @PostMapping("/{email}")
    fun createWork(@Valid @RequestBody newWork: Work,
                 @PathVariable email:String,
                 @RequestHeader("Authorization") token:String) : ResponseEntity<WorkDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.createWork(newWork,email,token).toDto())
    }

    @PutMapping("/editWork/{id}")
    fun editWork(@PathVariable id: Long,
                 @RequestHeader("Authorization") token:String,
                 @Valid @RequestBody newWork: Work)= workService.editWork(id, token,newWork)

    @DeleteMapping("/{id}")
    fun deleteWork(@PathVariable id: Long) =
        workService.deleteWork(id)

}