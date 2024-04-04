package com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.findmeongithub.R

class AdapterOfSectionPager(private val konteks: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var bundleFragment: Bundle = data

    init {
        bundleFragment = data
    }

    @StringRes
    private val TITLES_OF_TAB = intArrayOf(R.string.first_tab, R.string.second_tab)
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragmentFollows: Fragment? = null
        when (position) {
            0 -> fragmentFollows = FragmentOfFollowers()
            1 -> fragmentFollows = FragmentOfFollowing()
        }
        fragmentFollows?.arguments = this.bundleFragment
        return fragmentFollows as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return konteks.resources.getString(TITLES_OF_TAB[position])
    }
}
