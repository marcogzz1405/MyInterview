package garcia.marco.myinterview.data.api

import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.response.AddUserResponse
import garcia.marco.myinterview.data.remote.response.GetListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("sec_dev_interview/?")
    suspend fun getList(
        @Query("offset") offset: Int
    ): MutableList<GetListResponse>

    @GET("sec_dev_interview/?")
    suspend fun getListByLimit(
        @Query("limit") limit: Int
    ): MutableList<GetListResponse>

    @POST("sec_dev_interview/")
    suspend fun addUser(@Body user: AddUserRequest): AddUserResponse

}