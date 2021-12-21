package cnos.fap.project.models

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Work(

    @get:NotBlank(message = "{work.typeService.blank}")
    var serviceType:String,

    @get:NotBlank(message = "{work.materialPrint.blank}")
    var printMaterial:String,

    @get:NotNull(message = "{work.numberPrint.null}")
    var printNumber:Int,

    @get:NotNull(message = "{work.withColor.null}")
    var printColor:Boolean,

    var time:Int? = null,

    @get:NotNull(message = "{work.price.null}")
    var price:Double,

    var date:LocalDate? = LocalDate.now(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    var user:User?,

    var designWidth:String? = null,

    var designHeight:String? = null,

    var designText:String? = null,

    var printSize:String? = null,

    var printWidth:String? = null,

    var printHeight:String? = null,

    @Id @GeneratedValue
    var id:Long? = null
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Work
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}