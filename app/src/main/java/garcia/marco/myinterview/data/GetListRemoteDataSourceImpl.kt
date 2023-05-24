package garcia.marco.myinterview.data

import garcia.marco.myinterview.data.api.ApiService
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.data.repository.GetListRemoteDataSource
import javax.inject.Inject

class GetListRemoteDataSourceImpl @Inject constructor(val api: ApiService): GetListRemoteDataSource {
    override suspend fun getList(offset: Int): GetListResponse {
        return api.getList(offset)
    }
}