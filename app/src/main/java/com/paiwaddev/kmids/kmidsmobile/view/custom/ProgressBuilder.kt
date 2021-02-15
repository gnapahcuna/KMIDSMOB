package com.paiwaddev.kmids.kmidsmobile.view.custom

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import com.paiwaddev.kmids.kmidsmobile.R

class ProgressBuilder(private val context: Context) {
    private var dialog: Dialog

    init {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(
                context.resources.getDrawable(
                        R.drawable.dialog_progress_background))
        dialog.setContentView(R.layout.layout_progress_dialog)
        dialog.setCancelable(false)
    }

    fun showProgressDialog() {
        dialog.show()
    }

    fun getTextView(): TextView {
        return dialog.findViewById<View>(R.id.pbText) as TextView
    }

    fun getDialog(): Dialog {
        return dialog
    }

    fun dismissProgressDialog() {
        dialog.dismiss()
    }
}