package com.onepercent.moviesapp.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.onepercent.moviesapp.R
import com.onepercent.moviesapp.databinding.FragmentFeedBinding
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed), PopularMovieAdapter.OnItemClickListener {

    private val viewModel by viewModels<FeedViewModel>()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PopularMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFeedBinding.bind(view)

        init()
        initSwipeToRefresh()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: String) {
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }


    private fun init() {
        adapter = PopularMovieAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swiperefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
//                // Use a state-machine to track LoadStates such that we only transition to
//                // NotLoading from a RemoteMediator load if it was also presented to UI.
//                .asMergedLoadStates()
                // Only emit when REFRESH changes, as we only want to react on loads replacing the
                // list.
                .distinctUntilChangedBy { it.mediator?.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.mediator?.refresh is LoadState.NotLoading }
                // Scroll to top is synchronous with UI updates, even if remote load was triggered.
                .collect {
                    binding.recyclerView.scrollToPosition(0)
                }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefresh.setOnRefreshListener { adapter.refresh() }
    }
}