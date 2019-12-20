package com.ismin.opendataapp.pokapiclass

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var pokapiFragmentsTitle = ArrayList<String>()
    private var pokapiFragments = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return pokapiFragments.get(position)
    }

    override fun getCount(): Int {
        return pokapiFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pokapiFragmentsTitle.get(position)
    }

    fun addFragment(fragment: Fragment, fragmentName: String) {
        pokapiFragments.add(fragment)
        pokapiFragmentsTitle.add(fragmentName)
    }
}