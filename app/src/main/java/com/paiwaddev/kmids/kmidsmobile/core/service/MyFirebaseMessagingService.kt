package com.paiwaddev.kmids.kmidsmobile.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.R
import com.paiwaddev.kmids.kmidsmobile.view.ui.HomeActivity
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FCMService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG,"From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (true) {
                scheduleJob(remoteMessage.data)
            }else {
                handleNow()
            }
        }

    }


    override fun onNewToken(token: String) {
        //super.onNewToken(token)

        Log.d(TAG, token)
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task done")
    }

    private fun scheduleJob(data: MutableMap<String, String>) {

        val _jsonData = data.toString()
        val gson = Gson()
        val mPerson = gson.fromJson(_jsonData, FCMData::class.java)

        //sendNotification(personName.toString())
        Events.serviceEvent.postValue(mPerson)
    }
}

data class FCMData(
        val Type: String,
        val Persons: List<FCMPersons>,
)
data class FCMPersons(
        val PersonID: Int,
        val PersonName: String
)

object Events {
    val serviceEvent: MutableLiveData<FCMData> by lazy {
        MutableLiveData<FCMData>()
    }
}