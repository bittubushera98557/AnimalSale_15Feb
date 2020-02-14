package com.example.lenovo.emptypro.Utilities;


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.androidadvance.topsnackbar.TSnackbar
import com.example.lenovo.emptypro.Activities.LoginSignUp
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import kotlinx.android.synthetic.main.dialog_alert.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

public class   Utilities {
    @SuppressLint("SetTextI18n")
   public fun alertLogoutDialog(activity : Activity, msg : String) {
        val dialog =   Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = msg

        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
            SharedPrefUtil.setUserId(activity,"" )
            SharedPrefUtil.setUserEmail(activity,"" )
            SharedPrefUtil.setUserMobile(activity,"" )
            SharedPrefUtil.setUserFirstName(activity,"" )
               activity.startActivity(Intent(activity, LoginSignUp::class.java))
            activity.finish()
        }
        dialog.show()
    }
   /* @SuppressLint("SetTextI18n")
    fun alertDialogOtp(activity : Activity, msg : String) {
        val dialog =   Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = msg
        dialog.btnYes.text =  "OK"

        dialog.space.visibility= View.GONE
        dialog. btnNo.visibility= View.GONE
        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog. btnYes.setOnClickListener {
            dialog.dismiss()
            if (activity is SelectViewPager)
             activity.currentItem(0)
        }
        dialog.show()
    }*/
   @SuppressLint("SetTextI18n")
   fun callDialog(activity : Activity,number: String) {
       val dialog = Dialog(activity)
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
       dialog.setCancelable(false)
       dialog.setContentView(R.layout.dialog_alert)
       val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
       tvMsg.text = "       Do you want to Call now?       "
       val btnYes = dialog.findViewById<Button>(R.id.btnYes)

       val btnNo = dialog.findViewById<Button>(R.id.btnNo)
       btnNo.setOnClickListener { dialog.dismiss() }
       btnYes.setOnClickListener {
           dialog.dismiss()
           val callIntent =  Intent(Intent.ACTION_DIAL)
           callIntent.data = Uri.parse("tel:"+number)

           activity. startActivity(callIntent)
       }

       dialog.show()
   }
   fun dateFormat1( date : String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var outputDateStr = ""
        try {
            val date2 = inputFormat.parse(date)
            outputDateStr = outputFormat.format(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }
    fun isValidEmail(email: String): Boolean {
       return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
   }
   fun isConnected(context : Context):Boolean {
       val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       return if (Build.VERSION.SDK_INT < 23) {
           val activeNetworkInfo = connectivityManager.activeNetworkInfo
           activeNetworkInfo != null && activeNetworkInfo.isConnected
       } else {
           val nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
           return  if (nc == null) {
               false
           } else {
               nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                       nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
           }
       }
    }

   fun  snackBar(fl : View,msg : String){
      val snackbar = TSnackbar.make(fl, msg, TSnackbar.LENGTH_LONG)
      val snackbarView = snackbar.view
      snackbarView.setBackgroundColor(Color.parseColor("#000000"))
      val textView =  snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
      textView.setTextColor(Color.WHITE)
      snackbar.show()
  }
     fun enterNextReplaceFragment(container_id : Int,fr : Fragment, fragmentManager : FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(container_id, fr).commit()
    }
    fun enterNextAddFragment(container_id : Int,fr : Fragment, fragmentManager : FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
     //   transaction.addToBackStack(null)
        transaction.replace(container_id, fr).commit()
    }


    fun  booleanisLocationEnabled(activity : Activity) : Boolean
         {
           return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled;
        } else {
            // This is Deprecated in API 28
            val mode = Settings.Secure.getInt(activity.contentResolver, Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF)
            (mode != Settings.Secure.LOCATION_MODE_OFF)

        }
    }
    fun dialog(ctx : Context) : Dialog{
        val dialog =   Dialog(ctx as Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.show()
        return dialog
    }
  /*  @SuppressLint("SetTextI18n")
    fun alertDialogVersion(activity : Activity, msg : String) {
        val dialog =   Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
      //  val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        dialog.tvMsg.text = msg

       // val btnYes = dialog.findViewById<View>(R.id.btnYes) as Button
        dialog.btnYes.text =  "OK"

        dialog.space.visibility= View.GONE
         dialog.btnNo.visibility= View.GONE

        dialog.btnYes.setOnClickListener {
          //  dialog.dismiss()
            val market_uri = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID
            val intent =  Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(market_uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent .FLAG_ACTIVITY_NO_ANIMATION
            activity.startActivity(intent)
        }
        dialog.show()
    }*/
 }