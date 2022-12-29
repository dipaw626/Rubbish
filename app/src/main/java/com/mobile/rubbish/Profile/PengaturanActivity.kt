package com.mobile.rubbish.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.Status.StatusActivity
import com.mobile.rubbish.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PengaturanActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView
    private lateinit var ivstatus: ImageView
    private lateinit var ivhome: ImageView

    //firebase
    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        val phone = auth?.currentUser?.phoneNumber.toString()

        if(currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        readdata()

        ivback = findViewById(R.id.ivbackset)
        ivstatus = findViewById(R.id.ivstatus)
        ivhome = findViewById(R.id.ivhome)
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, ProfileActivity::class.java)
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

        val hapusAkun = findViewById<TextView>(R.id.idDeleteAkun)
        hapusAkun.setOnClickListener{
            buttonDelete()
        }

    }

    private fun readdata() {
        val phone = auth?.currentUser?.phoneNumber.toString()
        database.child(phone).get().addOnSuccessListener {
            if (it.exists()) {
                val editusername = findViewById<TextView>(R.id.tvusernameset)
                val editemail = findViewById<TextView>(R.id.tvemailset)
                val editalamat = findViewById<TextView>(R.id.tvalamatset)

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

    private fun deleteDatabaseandAuth() {
        //delete database and auth
        val phone = auth?.currentUser?.phoneNumber.toString()
        val user = FirebaseAuth.getInstance().currentUser
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.child(phone).removeValue()
                    Toast.makeText(application, "Akun berhasil dihapus.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@PengaturanActivity, RegistActivity::class.java))
                }
            }

    }

    private fun buttonDelete() {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus Akun")
            setMessage("Yakin ingin menghapus akun Anda ?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    deleteDatabaseandAuth()
                    dialogInterface.dismiss()
                }
            }
        }
        dialog.show()
    }


//        // Get auth credentials from the user for re-authentication.
//        val storedVerificationId = intent.getStringExtra("storedVerificationId")
//        var otp= otpGiven.text.toString().trim()
//        val credential = PhoneAuthProvider
//            .getCredential("$storedVerificationId", "password1234")
//        // Prompt the user to re-provide their sign-in credentials
//        user!!.reauthenticate(credential)
//            .addOnCompleteListener {
//                user!!.delete()
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Log.d("del", "User account deleted.")
//                        }
//                    }
//            }

}