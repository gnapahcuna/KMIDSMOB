package com.paiwaddev.kmids.kmidsmobile.view.custom

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.ui.LockScreenActivity
import com.paiwaddev.kmids.kmidsmobile.view.ui.SettingPinActivity
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.PINViewModel

class SigleAlertDialog(private var _activity: Activity) : DialogFragment() {

    private var viewModel: PINViewModel? = null

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_BUTTON = "KEY_BUTTON"

        fun newInstance(title: String, button: String, activity: Activity): SigleAlertDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_BUTTON, button)
            val fragment = SigleAlertDialog(activity)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        if(_activity is LockScreenActivity){
            viewModel =
                    ViewModelProvider(activity as LockScreenActivity).get(PINViewModel::class.java)
        }else if(_activity is SettingPinActivity){
            viewModel =
                    ViewModelProvider(activity as SettingPinActivity).get(PINViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragment_single_choice_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        dialog?.findViewById<TextView>(R.id.tvTitle)?.text = arguments?.getString(KEY_TITLE)
        val positive = dialog?.findViewById<Button>(R.id.btnPositive)
        positive?.text = arguments?.getString(KEY_BUTTON)
        positive?.setOnClickListener {
            viewModel?.onClearPin()
            dialog?.dismiss()
        }
    }
}