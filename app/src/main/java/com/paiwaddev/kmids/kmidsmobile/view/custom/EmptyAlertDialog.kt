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
import com.paiwaddev.kmids.kmidsmobile.R

class EmptyAlertDialog(private var _activity: Activity) : DialogFragment() {

    companion object {

        const val TAG = "EmptyDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_BUTTON = "KEY_BUTTON"

        fun newInstance(title: String, button: String, activity: Activity): EmptyAlertDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_BUTTON, button)
            val fragment = EmptyAlertDialog(activity)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


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
            dialog?.dismiss()
        }
    }
}