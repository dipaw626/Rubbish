package com.mobile.rubbish.PembayaranNextMonth

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
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
import com.mobile.rubbish.JenisPembayaran.JenisActivity
import com.mobile.rubbish.Login.LoginActivity
import com.mobile.rubbish.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class PilihanBulanActivity : AppCompatActivity() {

    private lateinit var ivback: ImageView
    private lateinit var transaksi3bulan: ImageView
    private lateinit var transaksi6bulan: ImageView
    private lateinit var transaksi12bulan: ImageView
    private lateinit var waktunow: TextView

    lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihan_bulan)

        supportActionBar?.hide()

        ivback = findViewById(R.id.ivbackbulan)
        transaksi3bulan = findViewById(R.id.iv3bulan)
        transaksi6bulan = findViewById(R.id.iv6bulan)
        transaksi12bulan = findViewById(R.id.iv12bulan)
        waktunow = findViewById(R.id.tglnow)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        //checkuser
        if(currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        //Set datetime
        val currentTime =  LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val time = currentTime.format(formatTime)
        waktunow.setText(time)




        ivback.setOnClickListener{
            val intent = Intent(applicationContext, JenisActivity::class.java)
            startActivity(intent)
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

        transaksi3bulan.setOnClickListener{
            val bulan = 3
            val tagihan = GlobalData.jumlah
//            val editbulan = bulan.toInt()
//            val edittextHarga = jumlah.text.toString()
//            val catatan = catatan.text.toString()
//            GlobalData.jumlah = edittextHarga.toInt()
//            GlobalData.catatan = catatan.toString()
//            val convertharga = edittextHarga.toInt()
//            val kalikan = convertharga * hargaproduct.toInt()
            val kalikan = tagihan * bulan.toDouble()



            //currentimemillis(memberikan id tagihan yg berbeda meskipun detik nya sama)
            val transactionRequest = TransactionRequest("rubbish-"+System.currentTimeMillis().toString()+ "",kalikan)

            val detail = ItemDetails("NamaItemId", GlobalData.jumlah.toDouble(), bulan.toInt(), "tagihan bulan $time")

            val itemDetails = ArrayList<ItemDetails>()
            itemDetails.add(detail)
            uiKitDetails(transactionRequest)

            // Set item details into the transaction request.
            transactionRequest.itemDetails = itemDetails

            //set transaction request into sdk instance(semua transaksirequest dijadikan satu di midtranssdk)
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this@PilihanBulanActivity)
        }

        transaksi6bulan.setOnClickListener{
            val bulan = 6
            val tagihan = GlobalData.jumlah
//            val editbulan = bulan.toInt()
//            val edittextHarga = jumlah.text.toString()
//            val catatan = catatan.text.toString()
//            GlobalData.jumlah = edittextHarga.toInt()
//            GlobalData.catatan = catatan.toString()
//            val convertharga = edittextHarga.toInt()
//            val kalikan = convertharga * hargaproduct.toInt()
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

            //set transaction request into sdk instance(semua transaksirequest dijadikan satu di midtranssdk)
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this@PilihanBulanActivity)
        }

        transaksi12bulan.setOnClickListener{
            val bulan = 12
            val tagihan = GlobalData.jumlah
//            val editbulan = bulan.toInt()
//            val edittextHarga = jumlah.text.toString()
//            val catatan = catatan.text.toString()
//            GlobalData.jumlah = edittextHarga.toInt()
//            GlobalData.catatan = catatan.toString()
//            val convertharga = edittextHarga.toInt()
//            val kalikan = convertharga * hargaproduct.toInt()
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

            //set transaction request into sdk instance(semua transaksirequest dijadikan satu di midtranssdk)
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this@PilihanBulanActivity)
        }


    }


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