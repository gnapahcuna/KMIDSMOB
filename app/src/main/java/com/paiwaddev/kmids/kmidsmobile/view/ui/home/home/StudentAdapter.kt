package com.paiwaddev.kmids.kmidsmobile.view.ui.home.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.data.model.Student

class StudentAdapter(private val studens: List<Student>): RecyclerView.Adapter<StudentViewHolder>() {
    private lateinit var v: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.custom_student_list,parent,false)
        val holder = StudentViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.tvStudentName.setText(studens.get(position).TitleName+" "
                +studens.get(position).FirstName+" "+studens.get(position).LastName)
    }

    override fun getItemCount(): Int {
        return studens.size
    }

}