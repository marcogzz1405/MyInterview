package garcia.marco.myinterview.data.repository

import garcia.marco.myinterview.data.remote.response.GetListResponse
import javax.inject.Inject

class GetListRepository @Inject constructor(private val getListRemoteDataSource: GetListRemoteDataSource) {

    suspend fun getList(offset: Int): GetListResponse {
        return getListRemoteDataSource.getList(offset)
    }

}

interface GetListRemoteDataSource {
    suspend fun getList(offset: Int): GetListResponse
}