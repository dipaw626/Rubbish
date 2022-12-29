package com.mobile.rubbish.Status

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.home.HomeActivity

class StatusActivity : AppCompatActivity() {
    private lateinit var ivback: ImageView
    private lateinit var ivakun: ImageView
    private lateinit var ivhome: ImageView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        ivback = findViewById(R.id.ivback)
        ivakun = findViewById(R.id.ivakun)
        ivhome = findViewById(R.id.ivhome)
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        ivakun.setOnClickListener{
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }
        ivhome.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity ::class.java)
            startActivity(intent)
        }

    }
}