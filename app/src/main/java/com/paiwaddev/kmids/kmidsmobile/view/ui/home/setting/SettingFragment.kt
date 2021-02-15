package com.paiwaddev.kmids.kmidsmobile.view.ui.home.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingModel
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingsAdapter
import com.paiwaddev.kmids.kmidsmobile.view.ui.SettingPinActivity
import com.paiwaddev.kmids.kmidsmobile.view.ui.SpashScreenActivity
import com.paiwaddev.kmids.kmidsmobile.viewModel.single.SettingViewModel
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class SettingFragment : Fragment(), SettingsAdapter.ItemListener, SettingsAdapter.SwitchListener {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var sharepref: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        sharepref = root.context.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)

        settingViewModel =
                ViewModelProvider(this).get(SettingViewModel::class.java)

        val rcv_setting = root.findViewById<RecyclerView>(R.id.rcv_settings)


        settingViewModel.onLoadIsTouchID(sharepref).observe(viewLifecycleOwner, {
            val items = ArrayList<SettingModel>()
            items.add(SettingModel("เปลี่ยนรหัสผ่าน", 1, it))
            items.add(SettingModel("เปิดใช้งาน touchID", 2, it))

            settingsAdapter = get { parametersOf(items, this, this) }
            settingsAdapter.setListener()

            rcv_setting.layoutManager = LinearLayoutManager(root.context)
            val itemDecor = DividerItemDecoration(root.context, DividerItemDecoration.VERTICAL)
            rcv_setting.addItemDecoration(itemDecor)

            rcv_setting.adapter = settingsAdapter
        })

        return root
    }

    override fun onItemClicked(setting: SettingModel) {
        println(setting.isActionId)
        if(setting.isActionId == 1){

            activity?.let{
                val intent = Intent (it, SettingPinActivity::class.java)
                intent.putExtra(SpashScreenActivity.KEYS,SpashScreenActivity.HOME_SCREEN)
                it.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                it.startActivity(intent)
                it.finish()
            }

        }
    }

    override fun onSwitchChange(isChecked: Boolean) {
        settingViewModel.onChageSwitchTouchID(sharepref, isChecked).observe(viewLifecycleOwner, {
            if (it)
                Toast.makeText(context, "เปิดใช้งาน Touch ID", Toast.LENGTH_SHORT).show()
        })
    }
}