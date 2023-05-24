package garcia.marco.myinterview.data.api

import garcia.marco.myinterview.data.remote.response.GetListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sec_dev_interview/?")
    suspend fun getList(
        @Query("offset") offset: Int
    ): GetListResponse

}