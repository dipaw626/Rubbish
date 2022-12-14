package com.mobile.rubbish.PembayaranTenggat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.JenisPembayaran.JenisActivity
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.R

class TagihanActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tagihan)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

//        if(currentUser==null){
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }

        ivback = findViewById(R.id.ivbacktagihan)
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, JenisActivity::class.java)
            startActivity(intent)
        }

    }
}