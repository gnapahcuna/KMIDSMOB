package com.paiwaddev.kmids.kmidsmobile.view.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.core.service.Events
import com.paiwaddev.kmids.kmidsmobile.data.model.Student
import com.paiwaddev.kmids.kmidsmobile.view.custom.DoubleAlertDialog
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.kmidsmobile.view.ui.home.home.HomeFragment
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import com.paiwaddev.kmids.kmidsmobile.viewModel.single.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity(), DoubleAlertDialog.ItemListener {

    private lateinit var locationManager: LocationManager

    private val homeViewModel: HomeViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        homeViewModel.getMenu()
        homeViewModel.isNormalRole.observe(this,{
           if(it)
               navView.menu.findItem(R.id.navigation_bus).isVisible = false
        })


        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home);
                }

                R.id.navigation_bus -> {
                    navController.navigate(R.id.navigation_bus);
                }

                R.id.navigation_setting -> {
                    navController.navigate(R.id.navigation_setting);
                }
                R.id.navigation_logout -> {
                    val alert = DoubleAlertDialog.newInstance(
                        getString(R.string.title_notifications), getString(
                            R.string.text_ok_button
                        ), getString(R.string.text_cancel_button)
                    )
                    alert.show(supportFragmentManager, SigleAlertDialog.TAG)
                    alert.setListener(this)
                }
            }
            true
        }

        locationManager = (getSystemService(LOCATION_SERVICE) as LocationManager?)!!

        subscribeTopic()

        Events.serviceEvent.observe(this,  { data ->
            homeViewModel.students().observe(this,{students ->

                val persons = ArrayList<Student>()
                for(person in data.Persons){
                    persons.add(students.filter { it.PersonID == person.PersonID  }.first())
                }

                if(persons.size>0) {
                    var name = ""
                    persons.forEach {
                        name += it.TitleName + "" + it.FirstName + " " + it.LastName+"\n"
                    }

                    var type: String? = null
                    when(data.Type){
                        "SCANED" -> {
                            type = "กำลังขึ้นรถ"
                        }
                        "JOURNEY" -> {
                            type = "อยู่ระหว่างทาง"
                        }
                        else -> {
                            type = "ถึงจุดหมายแล้ว"
                        }
                    }

                    homeViewModel.getStatusBus(students)
                    //Toast.makeText(this, name+type, Toast.LENGTH_LONG).show()
                    sendNotification(type,name)
                }
            })
        })
    }

    private fun sendNotification(title: String,messageBody: String) {
        /*val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0,
                intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = resources.getString(R.string.channel_id_message)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(
                this, channelId
        )
                .setSmallIcon(R.drawable.kmids_logo)
                .setContentTitle(resources.getString(R.string.title_notification_message))
                .setContentText(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())*/


        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(resources.getString(R.string.channel_id),
                    resources.getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = resources.getString(R.string.channel_decription)
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(applicationContext, resources.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.kmids_logo) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(messageBody)// message for notification
                .setAutoCancel(true) // clear notification after click

        val intent = Intent(applicationContext, HomeActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(0, mBuilder.build())
    }


    override fun onResume() {
        super.onResume()

        val intent_o = intent
        val user_id = intent_o.getStringExtra("nick")
        println(user_id)
    }

    private var TOPIC = "kmidsmob"
    private fun subscribeTopic() {
        // [START subscribe_topic]
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var message = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    message = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]
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

    override fun onItemClicked() {

        //Logout
        loginViewModel.onLogout().observe(this,{
            if(it){
                startActivity(Intent(this, WelcomeActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }
        })
    }


    public override fun onStart() {
        super.onStart()
        getLastLocation()
    }

    fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            return
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            System.out.println("lat: "+location.latitude.toString())
            homeViewModel.setLocation(
                    location.latitude.toString(),
                    location.longitude.toString()
            )
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }

}