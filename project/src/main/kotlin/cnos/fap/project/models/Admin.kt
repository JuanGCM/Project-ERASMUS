package cnos.fap.project.models

import javax.persistence.Entity

@Entity
class Admin(
    name:String,
    lastname:String,
    username:String,
    password:String,
    phone:Int,
    email:String,
    address:String,
    city:String,
    roles: MutableSet<String> = HashSet()
):User(name,lastname,username,password,phone,email,address,city,roles){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Admin
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}