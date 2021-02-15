package com.paiwaddev.kmids.kmidsmobile.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.custom.ProgressBuilder
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.PINViewModel
import org.koin.android.ext.android.inject

class SettingPinActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: PINViewModel
    private var pinNums: ArrayList<Button> = ArrayList()
    private var pins: ArrayList<View> = ArrayList()

    private lateinit var tvTitle: TextView
    private lateinit var pinNum0: Button
    private lateinit var pinNum1: Button
    private lateinit var pinNum2: Button
    private lateinit var pinNum3: Button
    private lateinit var pinNum4: Button
    private lateinit var pinNum5: Button
    private lateinit var pinNum6: Button
    private lateinit var pinNum7: Button
    private lateinit var pinNum8: Button
    private lateinit var pinNum9: Button
    private lateinit var pinDel: ImageView

    private lateinit var pin1: View
    private lateinit var pin2: View
    private lateinit var pin3: View
    private lateinit var pin4: View

    private lateinit var btnClose: ImageView

    private val dialog: ProgressBuilder by inject()

    var myPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_pin)

        viewModel =
                ViewModelProvider(this).get(PINViewModel::class.java)

        viewModel.getContext(this)

        initView()
        addPins()

        //ตรวจสอบเมื่อกด PIN ครบ 4 ตัว
        viewModel.mPIN.observe(this,{_pin ->

            //ใส่ PIN รอบแรก
            if(myPassword.isNullOrEmpty()) {
                Handler().postDelayed(Runnable {

                    tvTitle.text = resources.getString(R.string.text_title_input_pin2)
                    myPassword = _pin
                    //save

                    pins.forEach {
                        it.isPressed = false
                    }
                    viewModel.onClearPin()

                }, 400)
                return@observe

            }else{
                //รอบสอง
                if (myPassword != _pin) {

                    SigleAlertDialog.newInstance(getString(R.string.text_check_match_pin), getString(R.string.text_ok_button), SettingPinActivity()).show(supportFragmentManager, SigleAlertDialog.TAG)

                } else {
                    viewModel.onSavePIN(myPassword!!).observe(this,{

                        Toast.makeText(this,resources.getString(R.string.text_msg_create_pin_success),Toast.LENGTH_SHORT).show()

                        Handler().postDelayed(Runnable {
                            startActivity(Intent(this, HomeActivity::class.java))
                            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                            finish()

                        }, 400)
                    })

                }
            }

        })

        //รับ index เมื่อกด PIN
        viewModel.mIndexPIN.observe(this,{index ->
            println(index)
            pins.get(index).isPressed = true
        })

        //รหัสไม่ถูก
        viewModel.mLoginFail.observe(this,{fail ->
            println(fail)
            pins.forEach { it.isPressed = false }
        })

        //เมื่อกด PIN ให้ไป set ค่าที่ viewModel
        pinNums.forEach { num ->
            num.setOnClickListener {
                viewModel.onChekPin(num,pins.size)
            }
        }

        pinDel.setOnClickListener(this)
        btnClose.setOnClickListener(this)

    }

    fun initView() {
        tvTitle = findViewById(R.id.tvTitle_input_pin)
        pinNum0 = findViewById(R.id.pin0)
        pinNum1 = findViewById(R.id.pin1)
        pinNum2 = findViewById(R.id.pin2)
        pinNum3 = findViewById(R.id.pin3)
        pinNum4 = findViewById(R.id.pin4)
        pinNum5 = findViewById(R.id.pin5)
        pinNum6 = findViewById(R.id.pin6)
        pinNum7 = findViewById(R.id.pin7)
        pinNum8 = findViewById(R.id.pin8)
        pinNum9 = findViewById(R.id.pin9)
        pinDel = findViewById(R.id.del)

        pin1 = findViewById(R.id.pass1)
        pin2 = findViewById(R.id.pass2)
        pin3 = findViewById(R.id.pass3)
        pin4 = findViewById(R.id.pass4)

        btnClose = findViewById(R.id.btn_close)
    }

    fun addPins() {
        pinNums.let {
            it.add(pinNum0)
            it.add(pinNum1)
            it.add(pinNum2)
            it.add(pinNum3)
            it.add(pinNum4)
            it.add(pinNum5)
            it.add(pinNum6)
            it.add(pinNum7)
            it.add(pinNum8)
            it.add(pinNum9)
        }

        pins.let {
            it.add(pin1)
            it.add(pin2)
            it.add(pin3)
            it.add(pin4)
        }
    }

    private fun onClose(){
        val screen = intent.getStringExtra(SpashScreenActivity.KEYS)
        when(screen){
            SpashScreenActivity.HOME_SCREEN ->{
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }

            SpashScreenActivity.WELCOME_SCREEN ->{
                startActivity(Intent(this, WelcomeActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }

            SpashScreenActivity.LOCK_SCREEN ->{
                startActivity(Intent(this, LockScreenActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.del -> {
                viewModel.onDelPin(pins)
            }

            R.id.btn_close ->{
                onClose()
                //finish()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}