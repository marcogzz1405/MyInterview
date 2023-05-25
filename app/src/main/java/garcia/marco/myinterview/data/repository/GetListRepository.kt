package garcia.marco.myinterview.data.repository

import garcia.marco.myinterview.data.remote.response.GetListResponse
import javax.inject.Inject

class GetListRepository @Inject constructor(private val getListRemoteDataSource: GetListRemoteDataSource) {

    suspend fun getList(offset: Int): MutableList<GetListResponse> {
        return getListRemoteDataSource.getList(offset)
    }

    suspend fun getListByLimit(limit: Int): MutableList<GetListResponse> {
        return getListRemoteDataSource.getListByLimit(limit)
    }

}

interface GetListRemoteDataSource {
    suspend fun getList(offset: Int): MutableList<GetListResponse>
    suspend fun getListByLimit(limit: Int): MutableList<GetListResponse>
}