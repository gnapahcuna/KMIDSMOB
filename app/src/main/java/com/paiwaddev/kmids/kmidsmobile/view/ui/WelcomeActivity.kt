package com.paiwaddev.kmids.kmidsmobile.view.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.custom.EmptyAlertDialog
import com.paiwaddev.kmids.kmidsmobile.view.custom.ProgressBuilder
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.kmidsmobile.viewModel.single.LoginViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WelcomeActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var buttonNext: Button
    private lateinit var inputKeys: EditText

    private lateinit var dialog: ProgressBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        inputKeys = findViewById(R.id.input_key)
        buttonNext = findViewById(R.id.btn_next)

        buttonNext.isEnabled = false

        inputKeys.addTextChangedListener(this)
        inputKeys.setOnEditorActionListener(this)
        buttonNext.setOnClickListener(this)

        dialog = get { parametersOf(this) }

        loginViewModel.isLoading().observe(this, {
            if (it) {
                try {
                    dialog.showProgressDialog()
                } catch (e: Exception) {
                    println("e :" + e.message)
                }
            } else {
                dialog.dismissProgressDialog()
            }
        })

        loginViewModel.errorMessage().observe(this, {personID ->
            Toast.makeText(this, personID, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.loginData().observe(this, {
            if (it.size == 0) {
                val alert = EmptyAlertDialog.newInstance(
                    getString(R.string.text_error_lock_pin), getString(
                        R.string.text_ok_button
                    ), this
                )
                alert.show(supportFragmentManager, SigleAlertDialog.TAG)
            } else {
                if(it.first().MobileUserID==1) {
                    loginViewModel.onSavedID(inputKeys.text.toString())
                }else{
                    val alert = EmptyAlertDialog.newInstance(
                            getString(R.string.text_error_lock_pin), getString(
                            R.string.text_ok_button
                    ), this
                    )
                    alert.show(supportFragmentManager, SigleAlertDialog.TAG)
                }
            }
        })

        loginViewModel.isSavedID().observe(this, {
            if (it) {

                val intent = Intent(this, SettingPinActivity::class.java)
                intent.putExtra(SpashScreenActivity.KEYS, SpashScreenActivity.WELCOME_SCREEN)
                startActivity(Intent(intent))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }
        })

    }


    override fun onClick(v: View?) {

        loginViewModel.getLogin(inputKeys.text.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s!!.isNotEmpty()) {
            buttonNext.isEnabled = true
            buttonNext.background = resources.getDrawable(R.drawable.round_button_next)
        } else {
            buttonNext.isEnabled = false
            buttonNext.background = resources.getDrawable(R.drawable.round_button_empty)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        //
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_GO) {
            //sendMessage();
            handled = true;
        }
        return handled
    }


    private var COUNT_BACK: Int = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            COUNT_BACK++
            if (COUNT_BACK == 3) {
                finishAffinity()
                System.exit(0)
            }
            if (COUNT_BACK == 2)
                Toast.makeText(
                    this,
                    resources.getString(R.string.title_confirm_logout),
                    Toast.LENGTH_SHORT
                ).show()
            true
        } else super.onKeyDown(keyCode, event)
    }

}