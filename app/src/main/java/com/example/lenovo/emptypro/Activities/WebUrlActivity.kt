package com.example.lenovo.emptypro.Activities

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import com.example.lenovo.emptypro.R
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_web_url.*
import kotlinx.android.synthetic.main.header.*
import java.lang.Exception


class WebUrlActivity : AppCompatActivity(), View.OnClickListener {
    var recievedFrom = ""
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
              onBackPressed()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_url)
       img_back.setOnClickListener(this)

getOldIntentData()
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing=false
getOldIntentData()
        }
    }

    private fun getOldIntentData() {
        try {
            recievedFrom = intent.extras.getString("from")
             if (recievedFrom.equals("terms")) {
                tv_title.text = "Terms Condition "

/*
                strWebUrl = "http://www.vlovepets.com/terms/"
                webVw_url.settings.javaScriptEnabled = true
                webVw_url.settings.pluginState = PluginState.ON
                setContentView(webVw_url)
                webVw_url.loadUrl(strWebUrl)*/
                showWebView("http://www.vlovepets.com/terms/")

            } else if (recievedFrom.equals("privacy")) {
                /*   strWebUrl = "http://www.vlovepets.com/privacy/"
                   webVw_url.settings.javaScriptEnabled = true
                   webVw_url.settings.pluginState = PluginState.ON
                   setContentView(webVw_url)
                   webVw_url.loadUrl(strWebUrl)*/

                tv_title.text = "Privacy Policies"
                showWebView("http://www.vlovepets.com/privacy/")

            } else if (recievedFrom.equals("about")) {

/*                webVw_url.visibility=View.GONE
                tv_aboutUs.visibility=View.VISIBLE*/
                tv_title.text = "About Us"
                showWebView("http://www.vlovepets.com/about-us/")

            }


        } catch (exp: Exception) {

        }


    }

    private fun showWebView(strUrl: String) {
        gif_imgVw!!.visibility=VISIBLE

        Handler().postDelayed(Runnable {
            gif_imgVw!!.visibility= GONE
        },2500)
        webVw_url.webViewClient = MyWebViewClient(this)
        webVw_url.loadUrl(""+strUrl)

    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }

}
