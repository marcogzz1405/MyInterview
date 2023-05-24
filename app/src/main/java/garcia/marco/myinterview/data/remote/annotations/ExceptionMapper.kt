package garcia.marco.myinterview.data.remote.annotations

import garcia.marco.myinterview.data.remote.mappers.HttpExceptionMapper
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExceptionMapper(val value : KClass<out HttpExceptionMapper>)
