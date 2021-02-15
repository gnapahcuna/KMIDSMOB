package com.paiwaddev.kmids.kmidsmobile.view.ui.home.bus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.data.model.StatusBus
import com.paiwaddev.kmids.kmidsmobile.data.model.Student
import com.paiwaddev.kmids.kmidsmobile.view.custom.ProgressBuilder
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class BusFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var dialog: ProgressBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bus, container, false)

        homeViewModel.students().observe(viewLifecycleOwner,{students ->

            homeViewModel.statusBus().observe(viewLifecycleOwner,{statusBus ->

                if(statusBus.size>0) {
                    val frags = ArrayList<Fragment>()
                    for (index in 0..statusBus.size - 1) {
                        frags.add(ChildFragment(students[index], statusBus[index]))
                    }

                    val pagerAdapter: BusChildAdapter = get { parametersOf(childFragmentManager, frags) }
                    val pager = root.findViewById<ViewPager>(R.id.viewPager)
                    pager.adapter = pagerAdapter
                    val dotsIndicator = root.findViewById<SpringDotsIndicator>(R.id.dots_indicator)
                    dotsIndicator.setViewPager(pager)
                }
            })

        })


        dialog = get { parametersOf(activity) }

        homeViewModel.isLoading().observe(viewLifecycleOwner, {
            if (it) {
                try {
                    dialog.showProgressDialog()
                } catch (e: Exception) {
                    println("e :" + e.message)
                }
            } else {
                dialog.dismissProgressDialog()
            }

        })


        return root
    }
}