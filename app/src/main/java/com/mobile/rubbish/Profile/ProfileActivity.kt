package com.mobile.rubbish.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.Status.StatusActivity
import com.mobile.rubbish.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView
    private lateinit var ivstatus: ImageView
    private lateinit var ivhome: ImageView

    //firebase auth
    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        val phone = auth?.currentUser?.phoneNumber.toString()

        if(currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        readdata()

        ivback = findViewById(R.id.ivback)
        ivstatus = findViewById(R.id.ivstatus)
        ivhome = findViewById(R.id.ivhome)
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        ivstatus.setOnClickListener{
            val intent = Intent(applicationContext, StatusActivity::class.java)
            startActivity(intent)
        }
        ivhome.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity ::class.java)
            startActivity(intent)
        }
        val logout=findViewById<TextView>(R.id.idlogout)
        logout.setOnClickListener{
            logout()
        }


        val setall=findViewById<ImageView>(R.id.setAll)
        setall.setOnClickListener{
            val intent = Intent(applicationContext, SetAllActivity ::class.java)
            startActivity(intent)
        }

        val pengaturan=findViewById<TextView>(R.id.pengaturan)
        pengaturan.setOnClickListener{
            val intent = Intent(applicationContext, PengaturanActivity ::class.java)
            startActivity(intent)
        }


    }

    private fun readdata() {
        val phone = auth?.currentUser?.phoneNumber.toString()
        database.child(phone).get().addOnSuccessListener {
            if (it.exists()) {
                val editusername = findViewById<TextView>(R.id.tvusername)
                val editemail = findViewById<TextView>(R.id.tvemail)
                val editalamat = findViewById<TextView>(R.id.tvalamat)

                val username = it.child("username").value
                val email = it.child("email").value
                val alamat = it.child("alamat").value

                editusername.text = username.toString()
                editemail.text = email.toString()
                editalamat.text = alamat.toString()
            }else{
                Toast.makeText(this,"User Doesn't Exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logout() {
        val dialog = AlertDialog.Builder(this)
        val intent = Intent(this@ProfileActivity, LoginActivity::class.java )
        dialog.apply {
            setTitle("Konfirmasi Sign Out")
            setMessage("Yakin keluar akun ?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Keluar") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    auth.signOut()
                    startActivity(intent)
                    finish()
                    dialogInterface.dismiss()
                }
            }
        }
        dialog.show()
    }


}