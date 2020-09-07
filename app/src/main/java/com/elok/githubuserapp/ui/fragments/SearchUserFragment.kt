package com.elok.githubuserapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.elok.githubuserapp.R
import com.elok.githubuserapp.adapter.UserAdapter
import com.elok.githubuserapp.databinding.FragmentSearchUserBinding
import com.elok.githubuserapp.utilities.InjectorUtils
import com.elok.githubuserapp.viewmodels.SearchUserViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserFragment : Fragment() {
    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
        private var LAST_QUERY = ""
    }

    private var mBinding: FragmentSearchUserBinding? = null
    private val binding get() = mBinding!!
    private val mAdapter = UserAdapter()
    private var searchJob: Job? = null
    private val mViewModelSearch: SearchUserViewModel by viewModels {
        InjectorUtils.provideSearchUserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSearchUserBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.list.adapter = mAdapter

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_to_saved_fragment -> {
                    findNavController().navigate(R.id.action_searchUserFragment_to_savedUserFragment)
                    true
                }
                else -> false
            }
        }

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                binding.appbar.isSelected = binding.list.canScrollVertically(-1)
            }
        })

        mAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(context)
                    .setTitle(R.string.error)
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.retry) { _, _ ->
                        mAdapter.retry()
                    }
                    .show()
            }
        }

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query, 0L)
        initSearch()

        return binding.root
    }

    private fun initSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null)
                    search(p0, 0L)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                search(p0, 800L)
                return false
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(LAST_SEARCH_QUERY, LAST_QUERY)
    }

    private fun search(query: String?, timeDelay: Long) {
        // Make sure we cancel the previous job before creating a new one

        if (!query.isNullOrEmpty()) {
            LAST_QUERY = query
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(timeDelay)
                mViewModelSearch.searchUser(query).collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }
    }
}