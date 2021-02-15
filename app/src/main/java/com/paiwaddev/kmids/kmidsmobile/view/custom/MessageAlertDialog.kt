package com.paiwaddev.kmids.kmidsmobile.view.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MessageAlertDialog : DialogFragment() {

    val viewModel: HomeViewModel by sharedViewModel()

    companion object {

        const val TAG = "MessageAlertDialog"

        private const val KEY_TITLE = "KEY_TITLE"

        fun newInstance(title: String): MessageAlertDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = MessageAlertDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_alert_message_complete_dialog, container, false)
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

        Handler().postDelayed(Runnable {
            viewModel.isClearSeved()
            dialog?.dismiss()
        }, 2000)
    }
}