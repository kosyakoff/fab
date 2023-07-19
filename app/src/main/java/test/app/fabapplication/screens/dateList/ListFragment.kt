package test.app.fabapplication.screens.dateList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import test.app.fabapplication.R
import test.app.fabapplication.databinding.FragmentListBinding
import test.app.fabapplication.extensions.launchWhenStarted
import test.app.fabapplication.viewmodels.MainViewModel

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewBinding: FragmentListBinding by viewBinding()

    private val viewModel: MainViewModel by activityViewModels()

    private val itemListAdapter: DateListRecyclerViewAdapter = DateListRecyclerViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
    }

    private fun bindViewModel() {
        with(viewModel) {
            itemList.onEach { list ->
                itemListAdapter.submitList(list)
            }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)
        }
    }

    private fun initViews() {
        with(viewBinding) {
            recyclerView.adapter = itemListAdapter
        }
    }
}