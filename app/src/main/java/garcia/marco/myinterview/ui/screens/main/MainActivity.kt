package garcia.marco.myinterview.ui.screens.main

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.data.remote.response.GetListResponse
import garcia.marco.myinterview.databinding.ActivityMainBinding
import garcia.marco.myinterview.ui.bases.BaseActivity
import garcia.marco.myinterview.ui.utils.Constants
import garcia.marco.myinterview.ui.utils.onQueryTextListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var getListResponse: List<GetListResponse>? = null

    var adapter: GetListAdapter? = null

    override fun createView() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

    }

    override fun collectFlows() {
        with(binding) {
            lifecycleScope.launchWhenCreated {
                viewModel.result.collect {
                    when(it) {
                        is MainUiState.Loading -> {
                            loader.show()
                        }
                        is MainUiState.Waiting -> { }
                        is MainUiState.Error -> {
                            loader.hide()
                            showError(it.throwable)
                        }
                        is MainUiState.Success -> {
                            loader.hide()
                            getListResponse = it.getListResponse
                            updateAdapterGetListUsers(it.getListResponse)
                        }
                    }
                }
            }

            lifecycleScope.launchWhenResumed {
                svName.onQueryTextListener.collect { query ->
                    adapter?.filter?.filter(query)
                    /*val getListFilter = if (query.isNotEmpty()) {
                        getListResponse?.filter {
                            Log.d("MainActivity", "query: $query")
                            it.nombre == query
                        }
                    } else {
                        getListResponse
                    }
                    Log.d("MainActivity", "FilterList: ${getListFilter}")*/
                    //updateGetListUI(getListFilter as MutableList<GetListResponse>)
                }
            }

            lifecycleScope.launchWhenResumed {
                fabAdd.setOnClickListener {
                    goToAddUserActivity()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListByLimit(Constants.INITIAL_LIMIT)
    }

    private fun updateAdapterGetListUsers(getListResponse: MutableList<GetListResponse>) {
        adapter = GetListAdapter(getListResponse)
        binding.rvUsers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = adapter
    }

    private fun updateGetListUI(getListResponse: MutableList<GetListResponse>) {
        binding.rvUsers.recycledViewPool.clear()
        adapter?.update(getListResponse)
    }

}