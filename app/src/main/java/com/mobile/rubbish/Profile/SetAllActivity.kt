package com.mobile.rubbish.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.User
import com.mobile.rubbish.R

class SetAllActivity : AppCompatActivity() {

    //firebase auth
    var auth: FirebaseAuth? = null
    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_all)

        //firebase instance
        auth = FirebaseAuth.getInstance()
        var currentUser = auth!!.currentUser

        if(currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        readdata()

        val update=findViewById<TextView>(R.id.update)
        update.setOnClickListener{

            val editusername = findViewById<EditText>(R.id.username)
            val editemail = findViewById<EditText>(R.id.emailprofile)
            val editalamat = findViewById<EditText>(R.id.alamat)
            val username = editusername.text.toString()
            val email = editemail.text.toString()
            val alamat = editalamat.text.toString()

            updateData(username,email,alamat)
        }
    }

    private fun updateData(username: String, email: String, alamat: String) {
        val phone = auth?.currentUser?.phoneNumber.toString()
        val user = mapOf<String,String>(
            "username" to username,
            "email" to email,
            "alamat" to alamat
        )
        database.child(phone).updateChildren(user).addOnSuccessListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unsuccessful to Update", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readdata() {
        val phone = auth?.currentUser?.phoneNumber.toString()
        database.child(phone).get().addOnSuccessListener {
            if (it.exists()) {
                val editusername = findViewById<EditText>(R.id.username)
                val editemail = findViewById<EditText>(R.id.emailprofile)
                val editalamat = findViewById<EditText>(R.id.alamat)

                val username = it.child("username").value
                val email = it.child("email").value
                val alamat = it.child("alamat").value

                editusername.setText("$username")
                editemail.setText("$email")
                editalamat.setText("$alamat")
            }else{
                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
            }
        }
    }


//    private fun checkPhonePassExist(hp: String, pass: String) {
//        database.child(hp).child(pass).get().addOnSuccessListener {
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
//                Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
//                finish()
//            } else {
//                Toast.makeText(this@LoginActivity, "Password salah   ! ", Toast.LENGTH_LONG).show()
//            }
//
//        }
//            .addOnFailureListener {
//                Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
//            }
//    }

}