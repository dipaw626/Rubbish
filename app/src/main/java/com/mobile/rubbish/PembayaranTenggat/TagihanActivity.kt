//package com.mobile.rubbish.PembayaranTenggat
//
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
//import com.midtrans.sdk.corekit.core.MidtransSDK
//import com.midtrans.sdk.corekit.core.TransactionRequest
//import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
//import com.midtrans.sdk.corekit.models.*
//import com.midtrans.sdk.corekit.models.snap.CreditCard
//import com.midtrans.sdk.uikit.SdkUIFlowBuilder
//import com.mobile.rubbish.GlobalData
//import com.mobile.rubbish.JenisPembayaran.JenisActivity
//import com.mobile.rubbish.R
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.time.format.FormatStyle
//
//
//class TagihanActivity : AppCompatActivity() {
//
//    private lateinit var ivback: ImageView
//
//    lateinit var auth: FirebaseAuth
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_tagihan)
//
//        supportActionBar?.hide()
//
//        auth = FirebaseAuth.getInstance()
//        var currentUser = auth.currentUser
//
//
////        if(currentUser==null){
////            startActivity(Intent(this, LoginActivity::class.java))
////            finish()
////        }
//
//        ivback = findViewById(R.id.ivbacktagihan)
//        ivback.setOnClickListener{
//            val intent = Intent(applicationContext, JenisActivity::class.java)
//            startActivity(intent)
//        }
//
//
////        name.text = GlobalData.names
////        harga.text = "$ " + GlobalData.hargas.toString()
////        deskripsi.text = GlobalData.deskripsis
////        Picasso.get().load(GlobalData.photos).into(image)
//
//        SdkUIFlowBuilder.init()
//            .setClientKey("SB-Mid-client-EUkcZsfMJRaE9fIC")
//            .setContext(applicationContext)
//            .setTransactionFinishedCallback(TransactionFinishedCallback {
//                    result ->
//
//                if (result.status == "success") {
//                    Toast.makeText(this@TagihanActivity, "berhasil", Toast.LENGTH_SHORT).show()
//                }
//                if (result.status == "pending") {
//                    Toast.makeText(this@TagihanActivity, "pending", Toast.LENGTH_SHORT).show()
//                }
//                if (result.status == "invalid") {
//                    Toast.makeText(this@TagihanActivity, "invalid", Toast.LENGTH_SHORT).show()
//                }
//                if (result.status == "failed") {
//                    Toast.makeText(this@TagihanActivity, "failed", Toast.LENGTH_SHORT).show()
//                }
//            })
//            .setMerchantBaseUrl("https://app.sandbox.midtrans.com/snap/v1/transactions/")
//            .enableLog(true)
//            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
//            .setLanguage("id")
//            .buildSDK()
//
//        val pesan = findViewById<Button>(R.id.pesan)
//
//        pesan.setOnClickListener{
//            val bulan = GlobalData.bulan
//            val tagihan = GlobalData.jumlah
////            val editbulan = bulan.toInt()
////            val edittextHarga = jumlah.text.toString()
////            val catatan = catatan.text.toString()
////            GlobalData.jumlah = edittextHarga.toInt()
////            GlobalData.catatan = catatan.toString()
////            val convertharga = edittextHarga.toInt()
////            val kalikan = convertharga * hargaproduct.toInt()
//            val kalikan = tagihan * bulan.toDouble()
////            val kalikan = tagihan.toDouble()
//
//            //Set datetime
//            val currentTime =  LocalDateTime.now()
//            val formatTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
//            val time = currentTime.format(formatTime)
//
//            //currentimemillis(memberikan id tagihan yg berbeda meskipun detik nya sama)
//            val transactionRequest = TransactionRequest("rubbish-"+System.currentTimeMillis().toString()+ "",kalikan)
//
//            val detail = ItemDetails("NamaItemId", tagihan.toDouble(), bulan.toInt(), "tagihan bulan $time")
//            val itemDetails = ArrayList<ItemDetails>()
//            itemDetails.add(detail)
//            uiKitDetails(transactionRequest)
//
//            // Set item details into the transaction request.
//            transactionRequest.itemDetails = itemDetails
//
////            val creditCardOptions = CreditCard()
////            // Set to true if you want to save card to Snap
////            creditCardOptions.isSaveCard = false
////            // Set to true to save card token as `one click` token
////            creditCardOptions.isSecure()
////            // Set bank name when using MIGS channel
////            creditCardOptions.bank = BankType.BRI
////            // Set MIGS channel (ONLY for BCA, BRI and Maybank Acquiring bank)
////            creditCardOptions.channel = CreditCard.MIGS
////            // Set Credit Card Options
////            transactionRequest.creditCard = creditCardOptions
//
//            //set transaction request into sdk instance(semua transaksirequest dijadikan satu di midtranssdk)
//            MidtransSDK.getInstance().transactionRequest = transactionRequest
//            MidtransSDK.getInstance().startPaymentUiFlow(this)
//
//
//
//
//
////            pembayaran ?
////            logic nya
////            -set tagihan harusnya kan dari admin tuh? jadi gimana nih ngeset nya jumlah tagihannya?
////            val tagihan:Int = 150000
////            val bulan:Int = 1
//
////            -terus bulan, misal dia bayar terakhir bulan agustus.. (sept 1, okto 2, nov 3, des)
////            -terus dia bisa bayar tagihan terakhir pas desember (3 bulan setelah itu nonaktif)
//
////            looping bulan nya sampe 3 aja, setelah itu blacklist
////            if(target in 1..3) {
////                if (targ)
////            }
////            time.toInt()
//
//
////            Log.d("tampilkan", "${kalikan.toInt()}")
////            Log.d("tampilkan", kalikan.toString())
//        }
//
////        // Init custom settings
////        val uisetting = UIKitCustomSetting()
////        uisetting.isShowPaymentStatus = false // hide sdk payment status
////        MidtransSDK.getInstance().uiKitCustomSetting = uisetting
////
////        //skip customer detail
////        val setting = MidtransSDK.getInstance().uiKitCustomSetting
////        setting.setSkipCustomerDetailsPages(true)
////        MidtransSDK.getInstance().uiKitCustomSetting = setting
//
//    }
//
//    fun uiKitDetails(transactionRequest: TransactionRequest){
//
//        val phone = auth?.currentUser?.phoneNumber.toString()
//        val customerDetails = CustomerDetails()
//        customerDetails.customerIdentifier = "Dipa"
//        customerDetails.phone = phone
//        customerDetails.firstName = "Dipa"
//        customerDetails.lastName = "W"
//        customerDetails.email = "dipaw@students.amikom.ac.id"
//        val shippingAddress = ShippingAddress()
//        shippingAddress.address = "Bantul, Yogyakarta"
//        shippingAddress.city = "Bantul"
//        shippingAddress.postalCode = "51193"
//        customerDetails.shippingAddress = shippingAddress
//        val billingAddress = BillingAddress()
//        billingAddress.address  = "Bantul, Yogyakarta"
//        billingAddress.city = "Bantul"
//        billingAddress.postalCode = "51193"
//        customerDetails.billingAddress = billingAddress
//
//        transactionRequest.customerDetails = customerDetails
//    }
//
//}