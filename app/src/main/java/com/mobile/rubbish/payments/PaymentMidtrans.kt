//package com.mobile.rubbish.payments
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
//import com.midtrans.sdk.corekit.core.MidtransSDK
//import com.midtrans.sdk.corekit.core.TransactionRequest
//import com.midtrans.sdk.corekit.core.UIKitCustomSetting
//import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
//import com.midtrans.sdk.corekit.models.BillingAddress
//import com.midtrans.sdk.corekit.models.CustomerDetails
//import com.midtrans.sdk.corekit.models.ShippingAddress
//import com.midtrans.sdk.uikit.SdkUIFlowBuilder
//import com.mobile.rubbish.R
//
//
//class PaymentMidtrans : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_payment_midtrans)
//
////        name.text = GlobalData.names
////        harga.text = "$ " + GlobalData.hargas.toString()
////        deskripsi.text = GlobalData.deskripsis
////        Picasso.get().load(GlobalData.photos).into(image)
//
////        SdkUIFlowBuilder.init()
////            .setClientKey("your key")
////            .setContext(applicationContext)
////            .setTransactionFinishedCallback(TransactionFinishedCallback {
////                    result ->
////
////                if (result.status == "success") {
////                    Toast.makeText(this@PaymentMidtrans, "berhasil", Toast.LENGTH_SHORT).show()
////                }
////                if (result.status == "pending") {
////                    Toast.makeText(this@PaymentMidtrans, "pending", Toast.LENGTH_SHORT).show()
////                }
////                if (result.status == "invalid") {
////                    Toast.makeText(this@PaymentMidtrans, "invalid", Toast.LENGTH_SHORT).show()
////                }
////                if (result.status == "failed") {
////                    Toast.makeText(this@PaymentMidtrans, "failed", Toast.LENGTH_SHORT).show()
////                }
////            })
////            .setMerchantBaseUrl("https://app.sandbox.midtrans.com/snap/v1/transactions/")
////            .enableLog(true)
////            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
////            .setLanguage("id")
////            .buildSDK()
//
//
//        // Init custom settings
//        val uisetting = UIKitCustomSetting()
//        uisetting.isShowPaymentStatus = false // hide sdk payment status
//        MidtransSDK.getInstance().uiKitCustomSetting = uisetting
//
//        //skip customer detail
//        val setting = MidtransSDK.getInstance().uiKitCustomSetting
//        setting.setSkipCustomerDetailsPages(true)
//        MidtransSDK.getInstance().uiKitCustomSetting = setting
//
//    }
//
//    fun uiKitDetails(transactionRequest: TransactionRequest){
//
//        val customerDetails = CustomerDetails()
//        customerDetails.customerIdentifier = "Supriyanto"
//        customerDetails.phone = "082345678999"
//        customerDetails.firstName = "Supri"
//        customerDetails.lastName = "Yanto"
//        customerDetails.email = "supriyanto6543@gmail.com"
//        val shippingAddress = ShippingAddress()
//        shippingAddress.address = "Baturan, Gantiwarno"
//        shippingAddress.city = "Klaten"
//        shippingAddress.postalCode = "51193"
//        customerDetails.shippingAddress = shippingAddress
//        val billingAddress = BillingAddress()
//        billingAddress.address  = "Baturan, Gantiwarno"
//        billingAddress.city = "Klaten"
//        billingAddress.postalCode = "51193"
//        customerDetails.billingAddress = billingAddress
//
//        transactionRequest.customerDetails = customerDetails
//    }
//
//}