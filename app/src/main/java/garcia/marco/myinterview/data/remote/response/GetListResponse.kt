package garcia.marco.myinterview.data.remote.response

data class GetListResponse(
    val id: Int,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val edad: Int,
    val email: String,
    val fechaNac: String,
    val datos: Datos
)

data class Datos(
    val calle: String,
    val numero: String,
    val colonia: String,
    val municipio: String,
    val estado: String,
    val cp: String,
    val imagen: String? = null
)
