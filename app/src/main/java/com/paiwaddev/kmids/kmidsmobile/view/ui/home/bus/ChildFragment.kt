package com.paiwaddev.kmids.kmidsmobile.view.ui.home.bus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.data.model.StatusBus
import com.paiwaddev.kmids.kmidsmobile.data.model.Student
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ChildFragment(private val student: Student,private val statusBus: StatusBus): Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bus_child, container, false)

        val tvName: TextView = root.findViewById(R.id.tvChildName)
        val tvStatusName: TextView = root.findViewById(R.id.tv_status_name)
        val viewStatus: RelativeLayout = root.findViewById(R.id.view_bg_status)
        val imgRefresh: ImageView = root.findViewById(R.id.img_refresh)
        imgRefresh.visibility = View.GONE

        val name = student.TitleName+" "+student.FirstName+" "+student.LastName
        tvName.setText(name)

        homeViewModel.onStatusChange(student.PersonID).observe(viewLifecycleOwner,{
            tvStatusName.setText(it)
        })

       /* homeViewModel.isGetStatusBus(student.PersonID).observe(viewLifecycleOwner,{statusBus->
            tvStatusName.setText(statusBus.status.UpDownName)
        })*/

        /*imgRefresh.setOnClickListener {
            homeViewModel.getStatusBus(student.PersonID).observe(viewLifecycleOwner,{status ->
                *//*this.statusBus = status
                tvStatusName.setText(statusBus.UpDownName.toString())*//*
                homeViewModel.updateStatusBus(ShowStatusBus(student,status),indexed)
            })
        }*/

        /*val rcv = root.findViewById<RecyclerView>(R.id.rcv_timeline)
        busViewModel.itemsTimeLine.observe(viewLifecycleOwner,{
            println(it.size)
            val adapter: TimeLineAdapter = get { parametersOf(it) }
            rcv.layoutManager = LinearLayoutManager(activity)
            rcv.adapter = adapter
        })*/

        return root
    }
}
data class ShowStatusBus(
        val student: Student,
        val status : StatusBus
)
