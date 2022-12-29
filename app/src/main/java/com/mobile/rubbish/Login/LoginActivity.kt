package com.mobile.rubbish.Login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.LoginRegist.VerifyActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    //firebase auth
    var auth: FirebaseAuth? = null
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    //firebase realtime database
    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hide title bar
        getSupportActionBar()?.hide()

        val intentregist = findViewById<TextView>(R.id.tvtoregister)
        intentregist.setOnClickListener {
            val intent = Intent(applicationContext, RegistActivity::class.java)
            startActivity(intent)
        }

        //firebase instance
        auth = FirebaseAuth.getInstance()
        var currentUser = auth!!.currentUser

        //check user jika ada maka ke profile
        if (currentUser != null) {
            startActivity(Intent(applicationContext, ProfileActivity::class.java))
            finish()
        }

        val NoHP = findViewById<EditText>(R.id.etphonelogin)
        val login = findViewById<Button>(R.id.buttonLogin)
        //register requirement
        login.setOnClickListener {
            var phone = NoHP.text.toString()
            phone="+62"+phone
            checkPhoneExist(phone)
//           login()

        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//               signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                try {
                } catch (e: FirebaseAuthWeakPasswordException) {
                    Log.e(ContentValues.TAG, e.message!!)
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    Log.e(ContentValues.TAG, e.message!!)
                } catch (e: FirebaseAuthUserCollisionException) {
                    Log.e(ContentValues.TAG, e.message!!)
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, e.message!!)
                }
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext, VerifyActivityLogin::class.java)
                //send verif id to Verify Activity
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }
    }

    private fun checkPhoneExist(hp: String) {
        database.child(hp).get().addOnSuccessListener {

            if (it.exists()) {
                sendVerificationcode(hp)
            } else {
                Toast.makeText(this@LoginActivity, "Nomor Handphone tidak terdaftar, silahkan register.. ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, RegistActivity::class.java ))
            }
        } .addOnFailureListener {
                Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}


//    private fun login() {
//        val phone = findViewById<EditText>(R.id.etphonelogin)
//        var number = phone.text.toString().trim()
//
//        if (!number.isEmpty()) {
//            number = "+62" + number
//            sendVerificationcode(number)
//        } else {
//            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
//        }
//    }

//login.setOnClickListener {
//    var hp = NoHP.text.toString()
//    val password = pass.text.toString()
//
//    if (hp.isNotEmpty() && password.isNotEmpty()) {
//        hp = "+62" + hp
//        sendVerificationcode(hp)
//    }
//    if (password.isEmpty() && hp.isNotEmpty()) {
//        Toast.makeText(this@LoginActivity, "Password kosong ! ", Toast.LENGTH_SHORT).show()
//    }
//    if (hp.isEmpty() && password.isNotEmpty()) {
//        Toast.makeText(this@LoginActivity, "Nomor Handphone kosong ! ", Toast.LENGTH_SHORT)
//            .show()
//    }
//    if (password.isEmpty() && hp.isEmpty()) {
//        Toast.makeText(
//            this@LoginActivity,
//            "No Hp dan Password Kosong ! ",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//}

//    private fun checkPhonePassExist(hp: String, pass: String) {
//        database.child(hp).child(pass).get().addOnSuccessListener {
////            auth!!.signInWithCredential(credential).addOnSuccessListener {  }
//            if (it.exists()) {
//                //reference
////                val numphone = findViewById<>()
////                val uID = it.child("uid").value
////                val PhoneNumber = it.child("phone").value
//
//                //set text to ui
////                numphone.text =  PhoneNumber.toString()
//
//
//                Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
//                finish()
//            } else {
//                    Toast.makeText(this@LoginActivity, "Password salah   ! ", Toast.LENGTH_LONG).show()
//            }
//
//        }
//            .addOnFailureListener {
//                Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
//            }
//    }

//    private fun getdata() {
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                taskId
//                Log.e("tc", "onDataChange: $snapshot")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("tc", "onCancelled: $error" )
//            }
//
//        })
//    }







//    private fun login() {
//        val phone = findViewById<EditText>(R.id.etphonelogin)
////        val pass = findViewById<EditText>(R.id.etpasslogin)
//        var number = phone.text.toString().trim()
////        var password = pass.text.toString().trim()
//
//        if (!number.isEmpty()) {
//            number = "+62" + number
//            sendVerificationcode(number)
//        } else {
//            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun sendVerificationcode(number: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(number) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val phonenumber = findViewById<EditText>(R.id.etphonelogin)
//                    checkUserExist(phonenumber.text.toString())
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid ( INVALID OTP )
//                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
//                    }
//                    // Update UI
//                    startActivity(Intent(applicationContext, LoginActivity::class.java))
//                    finish()
//
//                }
//            }
//    }
//
//    private fun checkUserExist(phonenumber: String) {
//        FirebaseDatabase.getInstance().getReference("User").child(phonenumber)
//            .addValueEventListener(object : ValueEventListener {
//
//                override fun onCancelled(p0: DatabaseError) {
//                    Toast.makeText(this@LoginActivity, p0.message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    if (p0.exists()) {
//                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
//                        finish()
//                    } else {
//                        startActivity(Intent(this@LoginActivity, RegistActivity::class.java))
//                        finish()
//                    }
//                }
//            })
//    }



//    private fun login() {
//        val phone = findViewById<EditText>(R.id.etphonelogin)
//        val pass = findViewById<EditText>(R.id.etpasslogin)
//        var number= phone.text.toString().trim()
//        var password= pass.text.toString().trim()
//
//        if(!number.isEmpty() && !password.isEmpty()){
//            number="+62"+number
////            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
////                auth?.currentUser?.phoneNumber.toString(), number)
//            signInWithPhoneAuthCredential()
//        }else{
//            Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show()
//        }

//        if(number.isEmpty() && password.isEmpty()){
//            if (number.isEmpty()){
//                phone.error = "Nomor harus diisi !"
//                phone.requestFocus()
//                return login()
//            }
//            if (password.isEmpty()){
//                pass.error = "Password harus diisi !"
//                pass.requestFocus()
//                return login()
//            }
//            Toast.makeText(this, "Nomor Hp dan password masih kosong",Toast.LENGTH_SHORT).show()
//        }
//        else if(!Patterns.PHONE.matcher(number).matches()) {
//            phone.error = "Isikan no Hp dengan valid !"
//            phone.requestFocus()
//            return login()
//        }
//        else if(password.length < 6 && password.length > 20 ) {
//            pass.error = "Password minimal 6-20 karakter"
//            pass.requestFocus()
//            return login()
//        }
//        else {
//            number = "+62"+number
//            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
//                auth?.currentUser?.phoneNumber.toString(), number)
//            signInWithPhoneAuthCredential(credential)
//        }

//    }

//    private fun logincustomtoken() {
//        customToken?.let {
//            auth.signInWithCustomToken(it)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithCustomToken:success")
//                        val user = auth.currentUser
//                        updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithCustomToken:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
//                    }
//                }
//        }
//    }

//        val phone = findViewById<EditText>(R.id.etphonelogin)
//        var credential = auth.PhoneAuthCredential
//        signInWithPhoneAuthCredential()


//        binding.buttonLogin.setOnClickListener{
//            //fields
//            val Password = binding.etpasslogin.text.toString()
//            val Phone = binding.etphonelogin.text.toString()
//
//            //--validasi pass--
//            if (Password.isEmpty()){
//                binding.etpasslogin.error = "Password harus diisi !"
//                binding.etpasslogin.requestFocus()
//                return@setOnClickListener
//            }
//            //--validasi No. HP--
//            if (Phone.isEmpty()){
//                binding.etphonelogin.error = "Nomor Hp harus diisi !"
//                binding.etphonelogin.requestFocus()
//                return@setOnClickListener
//            }
////            if (!Patterns.PHONE.matcher(Phone).matches()){
////                binding.etphoneRegister.error = "Nomor Hp tidak terdaftar."
////                binding.etphoneRegister.requestFocus()
////                return@setOnClickListener
////            }
//
//            LoginFirebase(Phone, Password)
//
//        }

//sign in email
//    private fun LoginFirebase(phone: String, password: String) {
//        auth.signInWithEmailAndPassword(phone, password)
//            .addOnCompleteListener(this){
//                if(it.isSuccessful){
//                    Toast.makeText(this,"Selamat datang $phone", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
