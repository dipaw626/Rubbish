package com.mobile.rubbish.LoginRegist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import java.util.concurrent.TimeUnit


class RegistActivity : AppCompatActivity() {

    //reference

    //database auth
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    val database = FirebaseDatabase.getInstance().getReference("User")

    //firebase realtime database
//    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)


        //hide title bar
        getSupportActionBar()?.hide()

        //local reference
        val intentLogin = findViewById<TextView>(R.id.tvtologin)
        val regist = findViewById<Button>(R.id.buttonRegister)
        val phone = findViewById<EditText>(R.id.etphoneRegister)
//        val next = findViewById<Button>(R.id.buttonSelanjutnyaRegister)
//        val pass = findViewById<EditText>(R.id.etpassRegister)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth!!.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, ProfileActivity::class.java))
            finish()
//            Toast.makeText(applicationContext, "user $phone", Toast.LENGTH_LONG).show()
        }

        //reference
        auth = FirebaseAuth.getInstance()


        //intent to login
        intentLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        regist.setOnClickListener{
            var phone = phone.text.toString()
            phone="+62"+phone
            checkPhoneExist(phone)
        }



        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//               signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                try {
                } catch (e: FirebaseAuthWeakPasswordException) {
                    Log.e(TAG, e.message!!)
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, e.message!!)
                } catch (e: FirebaseAuthUserCollisionException) {
                    Log.e(TAG, e.message!!)
                } catch (e: Exception) {
                    Log.e(TAG, e.message!!)
                }
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
//                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
//
                var intent = Intent(applicationContext, VerifyActivity::class.java)

                //send verif id to Verify Activity

                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }

    }

    private fun checkPhoneExist(hp: String) {
        database.child(hp).get().addOnSuccessListener {

            if (it.exists()) {
                //reference
//                val numphone = findViewById<>()
//                val uID = it.child("uid").value
//                val PhoneNumber = it.child("phone").value

                //set text to ui
//                numphone.text =  PhoneNumber.toString()

                Toast.makeText(this@RegistActivity, "Nomor sudah terdaftar, silahkan login...", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@RegistActivity, LoginActivity::class.java))
                finish()
            } else {
//                Toast.makeText(this@RegistActivity, "Masukkan data dengan benar ! ", Toast.LENGTH_LONG).show()
                register()
            }

        }
            .addOnFailureListener {
                Toast.makeText(this@RegistActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    //--Register Phone--
    private fun register() {
        val phone = findViewById<EditText>(R.id.etphoneRegister)
        var number= phone.text.toString().trim()

        if(!number.isEmpty()){
            number="+62"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val phonenumber = findViewById<EditText>(R.id.etphoneRegister)
//                    checkUserExist(phonenumber.text.toString())
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid ( INVALID OTP )
//                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
//                    }
//                    // Update UI
//                    startActivity(Intent(applicationContext, VerifyActivity::class.java))
//                    finish()
//
//                }
//            }
//    }
//    private fun checkUserExist(phonenumber: String) {
//        FirebaseDatabase.getInstance().getReference("User").child(phonenumber)
//            .addValueEventListener(object : ValueEventListener {
//
//                override fun onCancelled(p0: DatabaseError) {
//                    Toast.makeText(this@RegistActivity, p0.message,Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    if (p0.exists()) {
//                        startActivity(Intent(this@RegistActivity, LoginActivity::class.java))
//                        finish()
//                    }
//                    else {
//                        val user = User(phonenumber)
//                        database.child(phonenumber).setValue(user)
//                        Log.d("num", "$user")
//                        intent.putExtra("phoneNumber", phonenumber)
//                        startActivity(Intent(this@RegistActivity, PasswordActivity::class.java))
//                        finish()
//                    }
//                }
//            })
//    }


    // --Register email--
//    private fun RegisterToFirebase(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this){
//                if(it.isSuccessful){
//                    Toast.makeText(this,"Register berhasil.", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }


//    binding.buttonRegisterAccount.setOnClickListener{
//        //fields
//        val Name = binding.etnameRegister.text.toString()
//        val Password = binding.etpassRegister.text.toString()
//        val Email = binding.etphoneRegister.text.toString()
//        val Address = binding.etaddressRegister.text.toString()
//
//        //--validasi nama--
//        if (Name.isEmpty()){
//            binding.etnameRegister.error = "Nama harus diisi !"
//            binding.etnameRegister.requestFocus()
//            return@setOnClickListener
//        }
//        //--validasi pass--
//        if (Password.isEmpty()){
//            binding.etpassRegister.error = "Password harus diisi !"
//            binding.etpassRegister.requestFocus()
//            return@setOnClickListener
//        }
//        //jangan lupa cari generate token pass
//        if (Password.length <= 6 && Password.length > 20) {
//            binding.etpassRegister.error = "Password minimal 6-20 karakter."
//            binding.etpassRegister.requestFocus()
//            return@setOnClickListener
//        }
//        //--validasi No. HP--
//        if (Email.isEmpty()){
//            binding.etphoneRegister.error = "Email harus diisi !"
//            binding.etphoneRegister.requestFocus()
//            return@setOnClickListener
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//            binding.etphoneRegister.error = "Email tidak valid."
//            binding.etphoneRegister.requestFocus()
//            return@setOnClickListener
//        }
//        //--validasi address--
//        if (Address.isEmpty()){
//            binding.etaddressRegister.error = "Alamat harus diisi !"
//            binding.etaddressRegister.requestFocus()
//            return@setOnClickListener
//        }
//
//        RegisterToFirebase(Email, Password)
//
//    }


}