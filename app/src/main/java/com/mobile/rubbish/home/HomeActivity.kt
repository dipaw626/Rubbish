package com.mobile.rubbish.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.JenisPembayaran.JenisActivity
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.Status.StatusActivity

class HomeActivity : AppCompatActivity() {

    //firebase
    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance().getReference("User")

    //local layout
    private lateinit var ivbayar: ImageView
    private lateinit var ivstatus: ImageView
    private lateinit var ivakun: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        val phone = auth?.currentUser?.phoneNumber.toString()

//        val tvphone =findViewById<TextView>(R.id.tvuser)
//        tvphone.setText(phone)

        if(currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        readdata()

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

    private fun readdata() {
        val phone = auth?.currentUser?.phoneNumber.toString()
        database.child(phone).get().addOnSuccessListener {
            if (it.exists()) {
                val editusername = findViewById<TextView>(R.id.tvuser)
                val editalamat = findViewById<TextView>(R.id.tvalamathome)

                val username = it.child("username").value
                val alamat = it.child("alamat").value

                editusername.text = username.toString()
                editalamat.text = alamat.toString()

            }else{
                Toast.makeText(this,"User Doesn't Exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

}