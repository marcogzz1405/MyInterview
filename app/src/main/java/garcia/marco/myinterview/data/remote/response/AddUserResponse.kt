package garcia.marco.myinterview.data.remote.response

data class AddUserResponse(
    val id: Int,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val edad: Int,
    val email: String,
    val fechaNac: String,
    val datos: Datos
)

data class DatosResponse(
    val calle: String,
    val numero: String,
    val colonia: String,
    val delegacion: String,
    val estado: String,
    val cp: String,
    val imagen: String
)