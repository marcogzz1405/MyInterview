package garcia.marco.myinterview.domain.exceptions

class NoInternetException : Exception() {
    val uiMessage = "¡Lo sentimos! Hubo un problema de comunicación. Por favor inténtalo más tarde."
}