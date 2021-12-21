package cnos.fap.project.dtos

import cnos.fap.project.models.User
import cnos.fap.project.models.Work
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class WorkDTO(

    @get:NotBlank(message = "{work.typeService.blank}")
    var serviceType:String,

    @get:NotBlank(message = "{work.materialPrint.blank}")
    var printMaterial:String,

    @get:NotNull(message = "{work.numberPrint.null}")
    var printNumber:Int,

    @get:NotNull(message = "{work.withColor.null}")
    var printColor:Boolean,

    var time: Int?,

    @get:Min(0, message = "{work.price.min}")
    @get:NotNull(message = "{work.price.null}")
    var price:Double,

    var date: LocalDate?,

    var user: User?,

    var designWidth:String?,

    var designHeight:String?,

    var designText:String?,

    var printSize:String?,

    var printWidth:String?,

    var printHeight:String?,

    var id:Long?
)
fun Work.toDto(): WorkDTO = WorkDTO(
    serviceType,
    printMaterial,
    printNumber,
    printColor,
    time,
    price,
    date,
    user,
    designWidth,
    designHeight,
    designText,
    printSize,
    printWidth,
    printHeight,
    id)