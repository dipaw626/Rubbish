package com.mobile.rubbish.JenisPembayaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.PembayaranNextMonth.PilihanBulanActivity
import com.mobile.rubbish.PembayaranNextMonth.TagihanNextMonthActivity
import com.mobile.rubbish.PembayaranTenggat.TagihanActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.home.HomeActivity

class JenisActivity : AppCompatActivity() {
    private lateinit var tvbyrtenggat: TextView
    private lateinit var tvbayarnext: TextView
    private lateinit var ivback: ImageView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jenis)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

//        if(currentUser==null){
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }

        tvbyrtenggat = findViewById(R.id.tvByrTenggat)
        tvbayarnext = findViewById(R.id.tvByrNext)
        ivback = findViewById(R.id.ivback)

        tvbyrtenggat.setOnClickListener{
            val intent = Intent(applicationContext, TagihanActivity::class.java)
            startActivity(intent)
        }
        tvbayarnext.setOnClickListener{
            val intent = Intent(applicationContext, PilihanBulanActivity::class.java)
            startActivity(intent)
        }
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}