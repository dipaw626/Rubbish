package com.mobile.rubbish.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.RegistActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.Status.StatusActivity
import com.mobile.rubbish.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView
    private lateinit var ivstatus: ImageView
    private lateinit var ivhome: ImageView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        //user kosong maka login dulu
//        if(currentUser==null){
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }

        ivback = findViewById(R.id.ivback)
        ivstatus = findViewById(R.id.ivstatus)
        ivhome = findViewById(R.id.ivhome)
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
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

        val logout=findViewById<ImageView>(R.id.idlogout)
        logout.setOnClickListener{
            logout()
        }


    }

    private fun logout() {
        val dialog = AlertDialog.Builder(this)
        val intent = Intent(this@ProfileActivity, LoginActivity::class.java )
        dialog.apply {
            setTitle("Konfirmasi Sign Out")
            setMessage("Yakin keluar akun ?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Keluar") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    auth.signOut()
                    startActivity(intent)
                    finish()
//                    Toast.makeText(application, "Delete Success ", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()

                }

            }
        }
        dialog.show()

    }


}