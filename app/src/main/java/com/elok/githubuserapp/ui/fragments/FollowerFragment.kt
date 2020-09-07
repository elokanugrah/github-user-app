package com.elok.githubuserapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.elok.githubuserapp.R
import com.elok.githubuserapp.databinding.FragmentFollowerBinding
import com.elok.githubuserapp.adapter.FollowerFollowingAdapter
import com.elok.githubuserapp.utilities.InjectorUtils
import com.elok.githubuserapp.viewmodels.FollowerViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FollowerFragment : Fragment() {

    private var mBinding: FragmentFollowerBinding? = null
    private val binding get() = mBinding!!
    private val mAdapter = FollowerFollowingAdapter()
    private var followerJob: Job? = null
    private val mViewModel: FollowerViewModel by viewModels {
        InjectorUtils.provideFollowerViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFollowerBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.followerList.adapter = mAdapter

//        subscribeUi(username)

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

        val username = requireArguments().getString(EXTRA_USERNAME)

        followerJob?.cancel()
        followerJob = lifecycleScope.launch {
            if (username != null) {
                mViewModel.userFollower(username).collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }

        return binding.root
    }

    /*private fun subscribeUi(username: String?) {
        followerJob?.cancel()
        followerJob = lifecycleScope.launch {
            if (username != null) {
                mViewModel.userFollower(username).collectLatest {
                    mAdapter.submitData(it)
                }
            }
        }
    }*/

    companion object {

        private const val EXTRA_USERNAME = "username"

        fun newInstance(username: String): FollowerFragment {
            return FollowerFragment().apply {
                arguments = Bundle(3).apply {
                    putString(EXTRA_USERNAME, username)
                }
            }
        }
    }
}