package com.paiwaddev.kmids.kmidsmobile.view.ui.home.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R

class StudentViewHolder(v: View): RecyclerView.ViewHolder(v) {
    var tvStudentName = v.findViewById<TextView>(R.id.tvStudentName)
}