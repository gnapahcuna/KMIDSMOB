package com.paiwaddev.kmids.kmidsmobile.view.ui.home.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.kmidsmobile.R

class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bus, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        return root
    }
}