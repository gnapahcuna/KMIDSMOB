package com.paiwaddev.kmids.kmidsmobile.view.custom

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
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.PINViewModel

class DoubleAlertDialog : DialogFragment() {

    private lateinit var viewModel: PINViewModel

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_BUTTON_POS = "KEY_BUTTON_POS"
        private const val KEY_BUTTON_NAG = "KEY_BUTTON_NAG"

        fun newInstance(title: String, buttonPOS: String, buttonNAG: String): DoubleAlertDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_BUTTON_POS, buttonPOS)
            args.putString(KEY_BUTTON_NAG, buttonNAG)
            val fragment = DoubleAlertDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_double_choice_dialog, container, false)
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
        val nagative = dialog?.findViewById<Button>(R.id.btnNagative)
        positive?.text = arguments?.getString(KEY_BUTTON_POS)
        nagative?.text = arguments?.getString(KEY_BUTTON_NAG)
        positive?.setOnClickListener {
            dialog?.dismiss()
            this.listener.onItemClicked()
        }
        nagative?.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private lateinit var listener: ItemListener

    interface ItemListener {
        fun onItemClicked()
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener;
    }
}