package garcia.marco.myinterview.ui.screens.main

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.databinding.ActivityMainBinding
import garcia.marco.myinterview.ui.bases.BaseActivity
import garcia.marco.myinterview.ui.utils.Constants
import garcia.marco.myinterview.ui.utils.onQueryTextListener
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun createView() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        viewModel.getList(Constants.INITIAL_OFFSET)

        binding.fabAdd.setOnClickListener {
            goToAddUserActivity()
        }
    }

    override fun collectFlows() {
        with(binding) {
            lifecycleScope.launchWhenCreated {
                viewModel.result.collect {
                    when(it) {
                        is MainUiState.Loading -> {
                            //loader.show()
                        }
                        is MainUiState.Waiting -> { }
                        is MainUiState.Error -> {
                            //loader.hide()

                        }
                        is MainUiState.Success -> {
                            //loader.hide()
                            updateGetListUsers(it.getListResponse)
                        }
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                svName.onQueryTextListener.collect {

                }
            }
        }
    }

    private fun updateGetListUsers(getListResponse: GetListResponse) {

    }

}