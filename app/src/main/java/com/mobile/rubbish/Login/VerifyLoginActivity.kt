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
//import com.google.firebase.auth.PhoneAuthProvider
//import com.mobile.rubbish.LoginRegist.PasswordActivity
//import com.mobile.rubbish.R
//
//class VerifyLoginActivity : AppCompatActivity() {
//
//    //databasereference
//    var auth: FirebaseAuth? = null
//    //firebase realtime database
////    lateinit var database: DatabaseReference
//    lateinit var phone: String
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_verify_login)
//
//        //local reference
//        val verify = findViewById<Button>(R.id.buttonVerifyLogin)
//        val otpGiven = findViewById<EditText>(R.id.id_otpLogin)
//
//        auth=FirebaseAuth.getInstance()
//
//        //get string
//        val storedVerificationId = intent.getStringExtra("storedVerificationIdlogin")
//
//        verify.setOnClickListener{
////            verifyCode(otpGiven.text.toString())
//
//            var otp= otpGiven.text.toString().trim()
//            if(!otp.isEmpty()){
//                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
//                    storedVerificationId.toString(), otp)
//                signInWithPhoneAuthCredential(credential)
//            }else{
//                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth!!.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
////                    val phone = findViewById<EditText>(R.id.etphoneRegister)
////                    var number= phone.text.toString().trim()
////                    intent.putExtra("phoneNumber", number)
////                    intent.putExtra("phoneNumber", phone)
////
//                    startActivity(Intent(applicationContext, PasswordAuthActivity::class.java))
//                    finish()
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//            }
//    }
//
//}