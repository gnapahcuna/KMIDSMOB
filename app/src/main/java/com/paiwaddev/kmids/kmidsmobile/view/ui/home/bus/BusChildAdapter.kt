package com.paiwaddev.kmids.kmidsmobile.view.ui.home.bus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paiwaddev.kmids.kmidsmobile.data.model.Student

class BusChildAdapter(fm: FragmentManager, private val fragments: List<Fragment>): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }
}