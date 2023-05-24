package garcia.marco.myinterview.data.remote.adapters

import garcia.marco.myinterview.data.remote.annotations.ExceptionMapper
import garcia.marco.myinterview.data.remote.mappers.HttpExceptionMapper
import garcia.marco.myinterview.domain.exceptions.ApiException
import garcia.marco.myinterview.domain.exceptions.NoInternetException
import garcia.marco.myinterview.domain.exceptions.UnexpectedException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.*
import java.io.IOException

class ErrorsCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>): Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call))
    }
}

class CallWithErrorHandling(
    private val delegate: Call<Any>
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, mapExceptionOfCall(call, HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapExceptionOfCall(call, t))
            }
        })
    }

    fun mapExceptionOfCall(call: Call<Any>, t: Throwable): Exception {
        val retrofitInvocation = call.request().tag(Invocation::class.java)
        val annotation = retrofitInvocation?.method()?.getAnnotation(ExceptionMapper::class.java)
        val mapper = try {
            annotation?.value?.java?.constructors?.first()
                ?.newInstance(retrofitInvocation.arguments()) as HttpExceptionMapper
        } catch (e: Exception) {
            null
        }
        return mapToDomainException(t, mapper)
    }

    private fun mapToDomainException(t: Throwable, httpExceptionsMapper: HttpExceptionMapper? = null): Exception {
        return when(t) {
            is IOException -> NoInternetException()
            is HttpException -> {
                httpExceptionsMapper?.map(t)?:when(t.code()) {
                    503 -> NoInternetException()
                    500 ->  {
                        val body = t.response()?.errorBody()?.string()?:"{\"message\":\"\"}"
                        try {
                            val jo = JSONObject(body)
                            val code = try {
                                jo.getString("code")
                            } catch (jsonEx : JSONException) {
                                ""
                            }
                            when(code) {
                                else -> ApiException(jo.getString("message"))
                            }

                        } catch (ex : Exception) {
                            UnexpectedException()
                        }
                    }
                    else -> UnexpectedException()
                }
            }
            else -> UnexpectedException()
        }
    }

    override fun clone() = CallWithErrorHandling(delegate.clone())
}