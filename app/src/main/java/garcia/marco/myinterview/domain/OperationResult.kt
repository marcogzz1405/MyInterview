package garcia.marco.myinterview.domain

/**
 * Clase que modela el resultado de una operación asincrona realizada por algún repositorio que recibe objetos del dominio
 */
sealed class OperationResult<out T: Any> {
    data class Success<out T : Any>(val data: T? = null) : OperationResult<T>()
    data class Error(val throwable: Throwable) : OperationResult<Nothing>()
}