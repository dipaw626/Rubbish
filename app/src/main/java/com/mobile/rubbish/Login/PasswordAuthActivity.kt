//package com.mobile.rubbish.Login
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.database.*
//import com.mobile.rubbish.LoginRegist.RegistActivity
//import com.mobile.rubbish.LoginRegist.User
//import com.mobile.rubbish.Profile.ProfileActivity
//import com.mobile.rubbish.R
//
//class PasswordAuthActivity : AppCompatActivity() {
//
////    private lateinit var database: DatabaseReference
//    var auth: FirebaseAuth? = null
//    val uid = auth?.currentUser?.uid.toString()
//    val phone = auth?.currentUser?.phoneNumber.toString()
//    val database = FirebaseDatabase.getInstance().getReference("User")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_password_auth)
//
//        auth = FirebaseAuth.getInstance()
//
////        val phone = auth?.currentUser?.phoneNumber.toString()
////        val uid = auth?.currentUser?.uid.toString()
//
//        val okAuth = findViewById<Button>(R.id.authOkLogin)
//        okAuth.setOnClickListener{
//
//            val pass = findViewById<EditText>(R.id.authpasswordLogin)
//            val password = pass.text.toString()
//
//            if(password.isNotEmpty()) {
//                checkPassExist(password)
//            }
//            else {
//                Toast.makeText(this@PasswordAuthActivity, "Password kosong", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//    }
//
//
//    private fun checkPassExist(password: String) {
//        database.child(uid).child(password).get().addOnSuccessListener {
//
//            if (it.exists()) {
//                //reference
////                val numphone = findViewById<>()
////                val uID = it.child("uid").value
////                val PhoneNumber = it.child("phone").value
//
//                //set text to ui
////                numphone.text =  PhoneNumber.toString()
//
//                Toast.makeText(this@PasswordAuthActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@PasswordAuthActivity, ProfileActivity::class.java))
//                finish()
//            } else {
//                Toast.makeText(this@PasswordAuthActivity, "Password salah ! ", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//            .addOnFailureListener {
//                Toast.makeText(this@PasswordAuthActivity, "Failed", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//
////    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
////        auth!!.signInWithCredential(credential)
////            .addOnCompleteListener(this) { task ->
////                if (task.isSuccessful) {
////                    // Sign in success, update UI with the signed-in user's information
////                    val phonenumber = findViewById<EditText>(R.id.etphonelogin)
////                    checkUserExist(phonenumber.text.toString())
////
////                } else {
////                    // Sign in failed, display a message and update the UI
////                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
////                        // The verification code entered was invalid ( INVALID OTP )
////                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
////                    }
////                    // Update UI
////                    startActivity(Intent(applicationContext, LoginActivity::class.java))
////                    finish()
////
////                }
////            }
////    }
//
//
//
//
//}