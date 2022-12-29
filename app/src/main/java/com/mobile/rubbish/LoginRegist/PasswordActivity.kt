package com.mobile.rubbish.LoginRegist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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

    //firebase auth
    var auth: FirebaseAuth? = null

    //firebase realtime database
    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        auth = FirebaseAuth.getInstance()

        val phone = auth?.currentUser?.phoneNumber.toString()
        val uid = auth?.currentUser?.uid.toString()

        val ok = findViewById<Button>(R.id.OK)
        ok.setOnClickListener{

            val pass = findViewById<EditText>(R.id.setpassword)
            val editusername = findViewById<EditText>(R.id.username)
            val editemail = findViewById<EditText>(R.id.emailprofile)
            val editalamat = findViewById<EditText>(R.id.alamat)
            val password = pass.text.toString()
            val username = editusername.text.toString()
            val email = editemail.text.toString()
            val alamat = editalamat.text.toString()

            if (password.isEmpty()){
                pass.error = "Password harus diisi !"
                pass.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                editemail.error = "Email harus diisi !"
                editemail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editemail.error = "Email tidak valid."
                editemail.requestFocus()
                return@setOnClickListener
            }
            if (username.isEmpty()){
                editusername.error = "Username harus diisi !"
                editusername.requestFocus()
                return@setOnClickListener
            }
            if (alamat.isEmpty()){
                editalamat.error = "Alamat harus diisi !"
                editalamat.requestFocus()
                return@setOnClickListener
            }

            val User = User(uid, phone, password, username, email, alamat)
            database.child(phone).setValue(User).addOnSuccessListener {
                pass.text.clear()
                editalamat.text.clear()
                editemail.text.clear()
                editusername.text.clear()

                Toast.makeText(this, "Akun berhasil dibuat.",Toast.LENGTH_SHORT).show()
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