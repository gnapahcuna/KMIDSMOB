package com.paiwaddev.kmids.kmidsmobile.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsTouchIDViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsViewHolder

class SettingsAdapter(private val data: List<SettingModel>, private val mListener: ItemListener, private val  mSwitchListener: SwitchListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(), CompoundButton.OnCheckedChangeListener {
    private val SECTION_PASSWORD:Int = 0
    private val SECTION_TOUCHID:Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder:RecyclerView.ViewHolder? = null
        when(viewType){
            SECTION_PASSWORD -> {
                val v =  LayoutInflater.from(parent.context).inflate(R.layout.change_password_custom, parent, false)
                holder = SettingsViewHolder(v)
                v.setOnClickListener{
                    this.listener.onItemClicked(data.get(0))
                }
            }
            SECTION_TOUCHID -> {
                val v =  LayoutInflater.from(parent.context).inflate(R.layout.change_touch_id_custom, parent, false)
                holder = SettingsTouchIDViewHolder(v)
                v.setOnClickListener{
                    this.listener.onItemClicked(data.get(1))
                }
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(vHolder: RecyclerView.ViewHolder, position: Int) {

        val type: Int = getItemViewType(position)
        if (type == SECTION_TOUCHID) {
            val item = data.get(position)
            val holder = vHolder as SettingsTouchIDViewHolder

            holder.switchOn.isChecked = data.get(position).isChecked
            holder.switchOn.setOnCheckedChangeListener(this)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if(data.get(position).isActionId == 1){
            return SECTION_PASSWORD
        }else{
            return SECTION_TOUCHID
        }
        return -1
    }

    private lateinit var listener: ItemListener

    interface ItemListener {
        fun onItemClicked(setting: SettingModel)
    }

    fun setListener() {
        this.listener = mListener;
        this.switchListener = mSwitchListener
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        this.switchListener.onSwitchChange(isChecked)
    }

    private lateinit var switchListener: SwitchListener

    interface SwitchListener {
        fun onSwitchChange(isChecked: Boolean)
    }

}

data class SettingModel(
        var settingName: String,         //name
        var isActionId: Int,
        var isChecked: Boolean//action id
)