package com.elok.githubuserapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.elok.githubuserapp.R
import com.elok.githubuserapp.adapter.FOLLOWER_LIST_PAGE_INDEX
import com.elok.githubuserapp.adapter.FOLLOWING_LIST_PAGE_INDEX
import com.elok.githubuserapp.adapter.FollowerFollowingPagerAdapter
import com.elok.githubuserapp.databinding.FragmentUserDetailViewPagerBinding
import com.elok.githubuserapp.utilities.InjectorUtils
import com.elok.githubuserapp.viewmodels.UserDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class UserDetailViewPagerFragment : Fragment() {

    private val args: UserDetailViewPagerFragmentArgs by navArgs()
    private var mBinding: FragmentUserDetailViewPagerBinding? = null
    private val binding get() = mBinding!!
    private val mViewModelUserDetail: UserDetailViewModel by viewModels {
        InjectorUtils.provideUserDetailViewModelFactory(requireActivity(), args.user.login)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail_view_pager, container,false)
        binding.apply {
            viewModel = mViewModelUserDetail
            lifecycleOwner = viewLifecycleOwner

            callback = object : Callback {
                override fun addOrDelete() {
                    mViewModelUserDetail.isSaved.value?.let { isSaved ->
                        Log.d("isSaved", isSaved.toString())
                        if (isSaved) {
                            mViewModelUserDetail.deleteSavedUser()
                        } else {
                            mViewModelUserDetail.addSavedUser(args.user)
                        }
                    }

                }
            }

            var isShow: Boolean
            var tempIsShown = true

            appbar.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    isShow = abs(verticalOffset) == appBarLayout.totalScrollRange
                    if (isShow != tempIsShown) {
                        tempIsShown = isShow
                        collapsingToolbar.isTitleEnabled = isShow
                        Log.d("Appbar", isShow.toString())
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            mViewModelUserDetail.getUserDetails().observe(viewLifecycleOwner) {
                viewPager.adapter = FollowerFollowingPagerAdapter(this@UserDetailViewPagerFragment, it.login)

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = getTabTitle(position, mapOf("follower" to it.followers, "following" to it.following))
                }.attach()
            }

            Glide.with(root).load(args.user.avatar_url).placeholder(R.drawable.ic_image_placeholder).circleCrop().into(avatar)

        }

        return binding.root
    }

    private fun getTabTitle(
        position: Int,
        followerFollowing: Map<String, Int>
    ): String? {
        return when (position) {
            FOLLOWER_LIST_PAGE_INDEX -> "${followerFollowing["follower"]} Followers"
            FOLLOWING_LIST_PAGE_INDEX -> "${followerFollowing["following"]} Followings"
            else -> null
        }
    }

    interface Callback {
        fun addOrDelete()
    }
}