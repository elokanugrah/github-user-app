package com.elok.githubuserapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elok.githubuserapp.ui.fragments.FollowerFragment
import com.elok.githubuserapp.ui.fragments.FollowingFragment

const val FOLLOWER_LIST_PAGE_INDEX = 0
const val FOLLOWING_LIST_PAGE_INDEX = 1

class FollowerFollowingPagerAdapter(fragment: Fragment, username: String) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        FOLLOWER_LIST_PAGE_INDEX to { FollowerFragment.newInstance(username) },
        FOLLOWING_LIST_PAGE_INDEX to { FollowingFragment.newInstance(username) }
    )

    override fun getItemCount(): Int {
        return tabFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}