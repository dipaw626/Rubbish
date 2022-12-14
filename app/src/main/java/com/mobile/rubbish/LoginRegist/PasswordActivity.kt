package com.mobile.rubbish.LoginRegist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityPasswordBinding
    private lateinit var database: DatabaseReference
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_password)

        auth = FirebaseAuth.getInstance()

        val phone = auth?.currentUser?.phoneNumber.toString()
        val uid = auth?.currentUser?.uid.toString()
//        val phone = intent.getStringExtra("phoneNumber").toString()
//        p.setText(phone)

        val ok = findViewById<Button>(R.id.OK)
        ok.setOnClickListener{

            val pass = findViewById<EditText>(R.id.setpassword)
            val password = pass.text.toString()

            database = FirebaseDatabase.getInstance().getReference("User")
            val User = User(uid, phone, password)
            database.child(phone).child(password).setValue(User).addOnSuccessListener {
                pass.text.clear()
//                Toast.makeText(this, "added",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "failed",Toast.LENGTH_SHORT).show()
            }

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