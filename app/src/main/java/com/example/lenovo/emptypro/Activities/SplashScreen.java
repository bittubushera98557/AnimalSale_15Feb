package com.example.lenovo.emptypro.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utils.SharedPrefUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         FirebaseApp.initializeApp(getApplicationContext());
        if (SharedPrefUtil.getDeviceToken(SplashScreen.this).equalsIgnoreCase("")) {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.e("SplashActivity token ", "" + token);
            SharedPrefUtil.setDeviceToken(SplashScreen.this, "" + token);
        }

       /* Intent intent = new Intent(getApplication(), WebUrlActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        intent.putExtra("from", "privacy");
        startActivity(intent);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefUtil.getUserId(SplashScreen.this).equalsIgnoreCase("")) {
                    Intent intentLoginSign = new Intent(SplashScreen.this, LoginSignUp.class);
                    startActivity(intentLoginSign);
                    finish();
                } else {
                    Intent intentLoginSign = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentLoginSign);
                    finish();
                }
            }
        }, 1500);
        //    printhashkey();

    }
    public void printhashkey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bittu.vlovepets",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
