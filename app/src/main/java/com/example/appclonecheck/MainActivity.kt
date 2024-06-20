package com.example.appclonecheck

import android.content.Intent
import android.content.pm.LabeledIntent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

//    init {
//        System.loadLibrary("native-lib-key")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFFFFF"));

//        val key = getAPIKey()
//        Log.v("harsh", "key == $key")

        val isGenuine = CloneChecker().isAppGenuine(this)

        val pathString = filesDir.path
        val data = "$pathString \n\n\n\n isClonedApp = ${!isGenuine} \n\n\n\n saved = ${CloneChecker.myPackage()}"
        val txt = findViewById<TextView>(R.id.txt)
        txt.text = data

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
//            val uri = "otpauth://totp/coindcx:harsh.barnwal+11@coindcx.com"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            startActivity(intent)

            emailAppIntent("test")
        }
        10.0.pow(-4.0)
    }

    private fun emailAppIntent(@NonNull chooserTitle: String): Boolean {
        val emailIntent = Intent(Intent.ACTION_VIEW, Uri.parse("otpauth:"))
        val packageManager = applicationContext.packageManager

        val activitiesHandlingEmails = packageManager.queryIntentActivities(emailIntent, 0)
        if (activitiesHandlingEmails.isNotEmpty()) {
            // use the first email package to create the chooserIntent
            val firstEmailPackageName = activitiesHandlingEmails.first().activityInfo.packageName
            val firstEmailInboxIntent = packageManager.getLaunchIntentForPackage(firstEmailPackageName)
            val emailAppChooserIntent = Intent.createChooser(firstEmailInboxIntent, chooserTitle)

            // created UI for other email packages and add them to the chooser
            val emailInboxIntents = mutableListOf<LabeledIntent>()
            for (i in 1 until activitiesHandlingEmails.size) {
                val activityHandlingEmail = activitiesHandlingEmails[i]
                val packageName = activityHandlingEmail.activityInfo.packageName
                packageManager.getLaunchIntentForPackage(packageName)?.let { intent ->
                    emailInboxIntents.add(
                        LabeledIntent(
                            intent,
                            packageName,
                            activityHandlingEmail.loadLabel(packageManager),
                            activityHandlingEmail.icon
                        )
                    )
                }
            }
            val extraEmailInboxIntents = emailInboxIntents.toTypedArray()
            val finalIntent = emailAppChooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraEmailInboxIntents)
            finalIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(finalIntent)
            return true
        } else {
            return false
        }
    }

//    external fun getAPIKey(): String

}