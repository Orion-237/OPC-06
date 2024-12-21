package docta

class Patient(val name: String, val age: Int, val desc: String = ""){
    override fun toString(): String = "$name $age years old"
}