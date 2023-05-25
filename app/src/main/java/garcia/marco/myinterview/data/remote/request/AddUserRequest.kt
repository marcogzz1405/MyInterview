package garcia.marco.myinterview.data.remote.request

data class AddUserRequest(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val edad: Int,
    val email: String,
    val fechaNac: String,
    val datos: String
)

data class Datos(
    val calle: String,
    val numero: String,
    val colonia: String,
    val delegacion: String,
    val estado: String,
    val cp: String,
    val imagen: String
)
