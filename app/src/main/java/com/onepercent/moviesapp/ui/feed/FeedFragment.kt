package com.onepercent.moviesapp.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
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
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swiperefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH changes, as we only want to react on loads replacing the
                // list.
                .distinctUntilChangedBy { it.mediator?.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.mediator?.refresh is LoadState.NotLoading }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swiperefresh.setOnRefreshListener { adapter.refresh() }
    }
}