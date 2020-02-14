package com.example.lenovo.emptypro.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService;
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance;
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utilities.Utilities;
import com.example.lenovo.emptypro.Utils.SharedPrefUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtp extends AppCompatActivity implements View.OnClickListener {
TextView btn_verifyOtp;
    EditText et_otp;
    String apiOTP="",phone="";
    String TAG="VerifyOtp ";
    Utilities utilities=new Utilities();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
    initComponent();
        btn_verifyOtp.setOnClickListener(this);

    }

    private void initComponent() {

        apiOTP=  getIntent().getExtras().getString("otp");
        phone=  getIntent().getExtras().getString("phone");
        btn_verifyOtp=(TextView)findViewById(R.id.btn_verifyOtp);
        et_otp=(EditText)findViewById(R.id.et_Otp);
      //  et_otp.setText(""+apiOTP);
        et_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
String str_otp=s.toString();
if(str_otp.length()>=6)
{
    btn_verifyOtp.setClickable(true);
    btn_verifyOtp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

}
    else {
    btn_verifyOtp.setTextColor(getResources().getColor(R.color.grey));
    btn_verifyOtp.setClickable(false);
    }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_verifyOtp:

                String strEt_Otp=et_otp.getText().toString();
                if(strEt_Otp.isEmpty() || strEt_Otp.equalsIgnoreCase("")) {
                    Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                 }
                else
                    if(strEt_Otp.equalsIgnoreCase(apiOTP))
                {
             callVerifyOtpForUserId();

                }
                     else {
                        Toast.makeText(this, "OTP Not Macthed", Toast.LENGTH_SHORT).show();
                }

        }
    }

    private void callVerifyOtpForUserId() {
        Log.e(TAG+" callGetOtpApi","verifyOTP/?phone="+   phone);
      final Dialog progreesDialog= utilities.dialog(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<AllApiResponse.VerifyOtpRes> call = service.verifyOtpApi(phone);

        call.enqueue(new Callback<AllApiResponse.VerifyOtpRes>() {
            @Override
            public void onResponse(Call<AllApiResponse.VerifyOtpRes> call, Response<AllApiResponse.VerifyOtpRes> response) {
                progreesDialog.cancel();
                Log.e(TAG+" callGetOtpApi","response   "+   response .toString());
                if(response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equals("success"))
                {

                      String strUserID=""+response.body().getUserID();

                    Log.e(TAG+" callGetOtpApi","strOTP="+   strUserID);


                    SharedPrefUtil.setUserId(VerifyOtp.this,""+strUserID);
                    SharedPrefUtil.setUserMobile(VerifyOtp.this,""+phone);

                    Intent intentLogin = new Intent(VerifyOtp.this, MainActivity.class);
                    startActivity(intentLogin);
                    finish();
                }
else{
                utilities.snackBar(et_otp,""+response.body().getMessageType());
                   Toast.makeText(VerifyOtp.this,""+response.body().getMessageType(),Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<AllApiResponse.VerifyOtpRes> call, Throwable t) {
                progreesDialog.cancel();

                Toast.makeText(VerifyOtp.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent intentBack= new Intent(VerifyOtp.this, LoginSignUp.class);
        startActivity(intentBack);
        finish();
    }
}
