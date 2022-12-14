package com.mobile.rubbish.PembayaranNextMonth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.JenisPembayaran.JenisActivity
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.R

class PilihanBulanActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView
    private lateinit var ivnext: ImageView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihan_bulan)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

//        if(currentUser==null){
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }

        ivback = findViewById(R.id.ivbackbulan)
        ivnext = findViewById(R.id.ivNextpilihanbulan)

        ivback.setOnClickListener{
            val intent = Intent(applicationContext, JenisActivity::class.java)
            startActivity(intent)
        }

        ivnext.setOnClickListener{
            val intent = Intent(applicationContext, TagihanNextMonthActivity::class.java)
            startActivity(intent)
        }

    }
}