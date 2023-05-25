package garcia.marco.myinterview.data.usescases

import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.response.AddUserResponse
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.data.repository.AddUserRepository
import garcia.marco.myinterview.data.repository.GetListRepository
import garcia.marco.myinterview.domain.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUserFlow @Inject constructor(private val repository: AddUserRepository) {

    suspend operator fun invoke(user: AddUserRequest): Flow<OperationResult<AddUserResponse>> = flow {
        try {
            val response = repository.addUser(user)
            emit(OperationResult.Success(response))
        } catch (ex: Exception){
            emit(OperationResult.Error(ex))
        }
    }.catch {
        emit(OperationResult.Error(it))
    }

}