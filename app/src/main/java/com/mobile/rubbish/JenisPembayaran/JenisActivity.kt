package com.mobile.rubbish.JenisPembayaran

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.mobile.rubbish.GlobalData
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.LoginRegist.User
import com.mobile.rubbish.PembayaranNextMonth.PilihanBulanActivity
import com.mobile.rubbish.Profile.ProfileActivity
import com.mobile.rubbish.R
import com.mobile.rubbish.home.HomeActivity
import com.mobile.rubbish.payments.pembayaran
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class JenisActivity : AppCompatActivity() {
    private lateinit var tvbyrtenggat: TextView
    private lateinit var tvbayarnext: TextView
    private lateinit var ivback: ImageView

    //firebase auth
    lateinit var auth: FirebaseAuth

    //firebase realtime database
    val databaseUser = FirebaseDatabase.getInstance().getReference("User")
    val databasePembayaran = FirebaseDatabase.getInstance().getReference("Pembayaran")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jenis)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-EUkcZsfMJRaE9fIC")
            .setContext(applicationContext)
            .setTransactionFinishedCallback(TransactionFinishedCallback {
                    result ->

//                    if (result.status == "success") {
//                        Toast.makeText(this@JenisActivity, "berhasil", Toast.LENGTH_SHORT).show()
//                    }
//                    if (result.status == "pending") {
//                        Toast.makeText(this@JenisActivity, "pending", Toast.LENGTH_SHORT).show()
//                    }
//                    if (result.status == "invalid") {
//                        Toast.makeText(this@JenisActivity, "invalid", Toast.LENGTH_SHORT).show()
//                    }
//                    if (result.status == "failed") {
//                        Toast.makeText(this@JenisActivity, "failed", Toast.LENGTH_SHORT).show()
//                    }
            })
            .setMerchantBaseUrl("https://app.sandbox.midtrans.com/snap/v1/transactions/")
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()


        tvbyrtenggat = findViewById(R.id.tvByrTenggat)
        tvbayarnext = findViewById(R.id.tvByrNext)
        ivback = findViewById(R.id.ivback)

        tvbyrtenggat.setOnClickListener{
            val bulan = GlobalData.bulan
            val tagihan = GlobalData.jumlah
            val kalikan = tagihan * bulan.toDouble()

            //Set datetime
            val currentTime =  LocalDateTime.now()
            val formatTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            val time = currentTime.format(formatTime)

            //currentimemillis(memberikan id tagihan yg berbeda meskipun detik nya sama)
            val transactionRequest = TransactionRequest("rubbish-"+System.currentTimeMillis().toString()+ "",kalikan)

            val detail = ItemDetails("NamaItemId", GlobalData.jumlah.toDouble(), bulan.toInt(), "tagihan bulan $time")

            val itemDetails = ArrayList<ItemDetails>()
            itemDetails.add(detail)
            uiKitDetails(transactionRequest)
            // Set item details into the transaction request.
            transactionRequest.itemDetails = itemDetails

            val idPembayaran = UUID.randomUUID().toString().substring(0,12)
            val phone = auth?.currentUser?.phoneNumber.toString()
            databaseUser.child(phone).get().addOnSuccessListener {
                if (it.exists()) {
                    val username = it.child("username").value.toString()

                    val Pembayaran = pembayaran(idPembayaran, phone, username, bulan, bulan*tagihan.toInt())
                    databasePembayaran.child(phone).child(idPembayaran).setValue(Pembayaran).addOnSuccessListener {
                        //set transaction request into sdk instance(semua transaksirequest dijadikan satu di midtranssdk)
                        MidtransSDK.getInstance().transactionRequest = transactionRequest
                        MidtransSDK.getInstance().startPaymentUiFlow(this@JenisActivity)

                    }.addOnFailureListener {
                        Toast.makeText(this, "failed",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this,"Pembayaran gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvbayarnext.setOnClickListener{
            val intent = Intent(applicationContext, PilihanBulanActivity::class.java)
            startActivity(intent)
        }
        ivback.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

    }

//    fun getRandomString(length: Int) : String {
//        val allowedChars = ('A'..'Z')+('0'..'9')
//        return (1..length)
//            .map { allowedChars.random() }
//            .joinToString("")
//    }


    fun uiKitDetails(transactionRequest: TransactionRequest){

        val phone = auth?.currentUser?.phoneNumber.toString()
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "Dipa"
        customerDetails.phone = phone
        customerDetails.firstName = "Dipa"
        customerDetails.lastName = "W"
        customerDetails.email = "dipaw@students.amikom.ac.id"
        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Bantul, Yogyakarta"
        shippingAddress.city = "Bantul"
        shippingAddress.postalCode = "51193"
        customerDetails.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.address  = "Bantul, Yogyakarta"
        billingAddress.city = "Bantul"
        billingAddress.postalCode = "51193"
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails
    }

}