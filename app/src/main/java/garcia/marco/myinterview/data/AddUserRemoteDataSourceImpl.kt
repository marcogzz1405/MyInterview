package garcia.marco.myinterview.data

import garcia.marco.myinterview.data.api.ApiService
import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.response.AddUserResponse
import garcia.marco.myinterview.data.repository.AddUserRemoteDataSource
import garcia.marco.myinterview.data.repository.GetListRemoteDataSource
import javax.inject.Inject

class AddUserRemoteDataSourceImpl @Inject constructor(val api: ApiService): AddUserRemoteDataSource {
    override suspend fun addUser(user: AddUserRequest): AddUserResponse {
        return api.addUser(user)
    }
}