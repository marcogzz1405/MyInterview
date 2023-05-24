package garcia.marco.myinterview.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.data.usescases.GetListFlow
import garcia.marco.myinterview.domain.OperationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getListFlow: GetListFlow): ViewModel() {

    private val _result = MutableStateFlow<MainUiState>(MainUiState.Waiting)
    val result : StateFlow<MainUiState> = _result

    fun getList(offset: Int) {
        viewModelScope.launch {
            val flow = getListFlow.invoke(offset)
            _result.value = MainUiState.Loading
            flow.collect {
                when(it) {
                    is OperationResult.Success -> _result.value = MainUiState.Success(it.data!!)
                    is OperationResult.Error -> _result.value = MainUiState.Error(it.throwable)
                }
            }
        }
    }

}

sealed class MainUiState {
    data class Success(val getListResponse: GetListResponse): MainUiState()
    data class Error(val throwable: Throwable): MainUiState()
    object Waiting : MainUiState()
    object Loading : MainUiState()
}