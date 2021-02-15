package com.paiwaddev.kmids.kmidsmobile.view.holder

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R

class SettingsTouchIDViewHolder(view: View): RecyclerView.ViewHolder(view){
    var switchOn = view.findViewById<Switch>(R.id.switch_touch_id)
}