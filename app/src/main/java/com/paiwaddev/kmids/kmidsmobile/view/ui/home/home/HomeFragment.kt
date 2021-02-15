package com.paiwaddev.kmids.kmidsmobile.view.ui.home.home

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.custom.MessageAlertDialog
import com.paiwaddev.kmids.kmidsmobile.view.custom.NotAreaAlertDialog
import com.paiwaddev.kmids.kmidsmobile.view.custom.ProgressBuilder
import com.paiwaddev.kmids.kmidsmobile.view.ui.HomeActivity
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment(), View.OnClickListener {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    private lateinit var dialog: ProgressBuilder

    private lateinit var sharepref: SharedPreferences

    private lateinit var buttonAccept: Button


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val rcvStudent: RecyclerView = root.findViewById(R.id.rcv_studens)
        rcvStudent.layoutManager = LinearLayoutManager(activity)
        val itemDocor = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rcvStudent.addItemDecoration(itemDocor)

        buttonAccept = root.findViewById(R.id.btnAccept)


        buttonAccept.setOnClickListener(this)

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



        homeViewModel.getUserId()
        homeViewModel.userId().observe(viewLifecycleOwner, {
            homeViewModel.getStudent(it)
            homeViewModel.userId().removeObservers(viewLifecycleOwner)
        })

        homeViewModel.students().observe(viewLifecycleOwner, {

            //get status bus
            homeViewModel.getStatusBus(it)

            if (it.size == 0) {
                buttonAccept.setBackgroundResource(R.drawable.round_button_empty)
                buttonAccept.isEnabled = false
            } else {
                buttonAccept.setBackgroundResource(R.drawable.round_button_next)
                buttonAccept.isEnabled = true

                val adapter = StudentAdapter(it)
                rcvStudent.adapter = adapter
                //rcvStudent.hasFixedSize()
            }
        })

        homeViewModel.isSaved().observe(viewLifecycleOwner, {
            if (it)
                MessageAlertDialog.newInstance(getString(R.string.text_alert_message)).show(
                        activity?.supportFragmentManager!!,
                        MessageAlertDialog.TAG
                )
        })

        homeViewModel.errorMessage().observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            buttonAccept.setBackgroundResource(R.drawable.round_button_empty)
            buttonAccept.isEnabled = false
        })

        homeViewModel.isRegister().observe(viewLifecycleOwner, {
            if (it) {
                buttonAccept.setBackgroundResource(R.drawable.round_button_empty)
                buttonAccept.isEnabled = false
            }
        })

        return root
    }

    override fun onClick(v: View?) {
        buttonAccept.isEnabled = false
        (activity as HomeActivity?)?.getLastLocation()

        homeViewModel.isLocation().observe(viewLifecycleOwner, { location ->
            println(location.first + ", " + location.second)
            homeViewModel.getDistanceArea(location.first, location.second).observe(viewLifecycleOwner, {
                if (it.first) {
                    homeViewModel.students().observe(viewLifecycleOwner, { students ->
                        homeViewModel.userId().observe(viewLifecycleOwner, {

                            homeViewModel.insetParent(students, it)

                            //clear observe data
                            homeViewModel.userId().removeObservers(viewLifecycleOwner)
                            homeViewModel.students().removeObservers(viewLifecycleOwner)

                            buttonAccept.isEnabled = true
                        })
                    })
                } else {
                    val alert = NotAreaAlertDialog.newInstance(
                            getString(R.string.text_error_area) , getString(
                            R.string.text_ok_button
                    ), activity as Activity
                    )
                    alert.show(childFragmentManager, NotAreaAlertDialog.TAG)

                    buttonAccept.isEnabled = true
                }

            })
            homeViewModel.isLocation().removeObservers(viewLifecycleOwner)
        })
    }
}