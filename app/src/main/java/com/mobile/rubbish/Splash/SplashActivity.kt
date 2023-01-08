package com.mobile.rubbish.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R

class SplashActivity : AppCompatActivity() {

    private val SplashScreenTime: Long = 1000 //1 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val skip = findViewById<TextView>(R.id.skip)
        skip.setOnClickListener{
            val skip = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(skip)
        }

        Handler().postDelayed( {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }, SplashScreenTime)

    }
}