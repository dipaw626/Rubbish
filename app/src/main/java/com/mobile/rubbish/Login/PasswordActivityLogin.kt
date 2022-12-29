package com.mobile.rubbish.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.databinding.ActivityPasswordBinding

class PasswordActivityLogin : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().getReference("User")
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_login)

        auth = FirebaseAuth.getInstance()

//        val phone = auth?.currentUser?.phoneNumber.toString()
//        val uid = auth?.currentUser?.uid.toString()
//        p.setText(phone)
//        Log.e("add", "nomor"+phone)

        val ok = findViewById<Button>(R.id.OKPass)
        ok.setOnClickListener{

            val pass = findViewById<EditText>(R.id.cekPassword)
            val password = pass.text.toString()
            checkPassExist(password)
        }
    }

    private fun checkPassExist(pass: String) {
        val phone = auth?.currentUser?.phoneNumber.toString()
        database.child(phone).get().addOnSuccessListener {

            if (it.exists()) {
                if (it.child("password").value == pass) {
                    Toast.makeText(this@PasswordActivityLogin, "Login berhasil. ", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@PasswordActivityLogin, ProfileActivity::class.java))
                    finish()
                } else{
                    Toast.makeText(this@PasswordActivityLogin, "Password salah ! ", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }

            }
        }.addOnFailureListener {
                Toast.makeText(this@PasswordActivityLogin, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

}

//class PasswordActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityPasswordBinding
//    private lateinit var database: DatabaseReference
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPasswordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.OK.setOnClickListener{
//
//            val password = binding.setpassword.text.toString()
//
//            database = FirebaseDatabase.getInstance().getReference("User")
//            val user = User(password)
//            database.child(password).setValue(user).addOnSuccessListener {
//                binding.setpassword.text.clear()
//
//                Toast.makeText(this, "added",Toast.LENGTH_SHORT).show()
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "failed",Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }
//}