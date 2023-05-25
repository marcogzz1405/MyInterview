package garcia.marco.myinterview.data.usescases

import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.data.repository.GetListRepository
import garcia.marco.myinterview.domain.OperationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListFlow @Inject constructor(private val repository: GetListRepository) {

    suspend operator fun invoke(offset: Int): Flow<OperationResult<MutableList<GetListResponse>>> = flow {
        try {
            val response = repository.getList(offset)
            emit(OperationResult.Success(response))
        } catch (ex: Exception){
            emit(OperationResult.Error(ex))
        }
    }.catch {
        emit(OperationResult.Error(it))
    }

}