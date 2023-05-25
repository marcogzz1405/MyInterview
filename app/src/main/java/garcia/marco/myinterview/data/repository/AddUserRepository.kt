package garcia.marco.myinterview.data.repository

import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.response.AddUserResponse
import garcia.marco.myinterview.data.remote.response.GetListResponse
import javax.inject.Inject

class AddUserRepository @Inject constructor(private val addUserRemoteDataSource: AddUserRemoteDataSource) {

    suspend fun addUser(user: AddUserRequest): AddUserResponse{
        return addUserRemoteDataSource.addUser(user)
    }

}

interface AddUserRemoteDataSource {
    suspend fun addUser(user: AddUserRequest): AddUserResponse
}