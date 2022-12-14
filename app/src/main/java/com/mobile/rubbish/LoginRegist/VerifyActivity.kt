package com.mobile.rubbish.LoginRegist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.databinding.ActivityRegistBinding
import com.mobile.rubbish.databinding.ActivityVerifyBinding
import com.mobile.rubbish.home.HomeActivity

class VerifyActivity : AppCompatActivity() {

    //databasereference
    var auth: FirebaseAuth? = null
    //firebase realtime database
//    lateinit var database: DatabaseReference
    lateinit var phone: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        //local reference
        val verify = findViewById<Button>(R.id.buttonVerify)
        val otpGiven = findViewById<EditText>(R.id.id_otp)

        auth=FirebaseAuth.getInstance()

//        val p = findViewById<TextView>(R.id.phone)
//        phone = intent.getStringExtra("phoneNumber")!!
//        Log.d("phone", phone)
//        p.setText(phone)

//        val code = intent.getStringExtra("code")

        //get string
        val storedVerificationId = intent.getStringExtra("storedVerificationId")


        verify.setOnClickListener{
//            verifyCode(otpGiven.text.toString())

            var otp= otpGiven.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    val phone = findViewById<EditText>(R.id.etphoneRegister)
//                    var number= phone.text.toString().trim()
//                    intent.putExtra("phoneNumber", number)
//                    intent.putExtra("phoneNumber", phone)
//
                    startActivity(Intent(applicationContext, PasswordActivity::class.java))
                    finish()

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }

                }
            }
    }

//    private fun verifyCode(authCode: String, enterCode: String) {
//        val credential = PhoneAuthProvider.getCredential(authCode, enterCode)
//        signInWithCredential(credential)
//    }
//    private fun signInWithCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
////                    val phone = findViewById<EditText>(R.id.etphoneRegister)
////                    var number= phone.text.toString().trim()
////                    intent.putExtra("phoneNumber", number)
////                    intent.putExtra("phoneNumber", phone)
////
//                    startActivity(Intent(applicationContext, PasswordActivity::class.java))
//                    finish()
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//            }
//    }

}