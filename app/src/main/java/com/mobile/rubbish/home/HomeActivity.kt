package com.mobile.rubbish.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.JenisPembayaran.JenisActivity
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.Status.StatusActivity

class HomeActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    //reference layout
    private lateinit var ivbayar: ImageView
    private lateinit var ivstatus: ImageView
    private lateinit var ivakun: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

//        if(currentUser==null){
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }

        ivbayar = findViewById(R.id.ivbyr)
        ivstatus = findViewById(R.id.ivstatus)
        ivakun = findViewById(R.id.ivakun)

        ivbayar.setOnClickListener{
            val intent = Intent(applicationContext, JenisActivity::class.java)
            startActivity(intent)
        }
        ivstatus.setOnClickListener{
            val intent = Intent(applicationContext, StatusActivity::class.java)
            startActivity(intent)
        }
        ivakun.setOnClickListener{
            val intent = Intent(applicationContext, ProfileActivity ::class.java)
            startActivity(intent)
        }


    }
}