package com.example.lenovo.emptypro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.ApiCallClasses.MainRepository;
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService;
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance;
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utils.SharedPrefUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
String TAG="LoginScreen ";    TextView btn_Login;
    EditText et_PhoneNum;
    CallbackManager callbackManager;
    private Disposable disposable;
    private MainRepository mainRepository;
    ProgressBar progress_bar;

    LoginButton btn_fbLogin;
    String token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initComponent();
        FirebaseApp.initializeApp(LoginScreen.this);
        if(SharedPrefUtil.getDeviceToken(LoginScreen.this).equalsIgnoreCase("")){
            token = FirebaseInstanceId.getInstance().getToken();
            SharedPrefUtil.setDeviceToken(LoginScreen.this,""+token);
        }
        Log.e("SplashActivity token ", "" + token);
        callbackManager = CallbackManager.Factory.create();
        btn_Login.setOnClickListener(this);

        btn_fbLogin= (LoginButton) findViewById(R.id.btn_fbLogin);
        btn_fbLogin.setReadPermissions("email,publish_actions");
        LoginManager.getInstance().logOut();
btn_fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("btn_fbLogin ","registerCallback onSuccess "+loginResult.getAccessToken().toString());

                getUserInfo(loginResult); }

            @Override
            public void onCancel() {
                Log.e("btn_fbLogin ","registerCallback oncancel");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("btn_fbLogin","oregisterCallback nError "+exception.toString());
                // App code
            }
        });

        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("FacebookCallback","onSuccess "+loginResult.getAccessToken().toString());
                        getUserInfo(loginResult);
                }

                    @Override
                    public void onCancel() {
                        Log.e("FacebookCallback","onCancel");
                        // App code
                    }

                    public
                    @Override void onError(FacebookException exception) {
                        Log.e("FacebookCallback","onError "+exception.toString());
                        // App code
                    }
                });

    }

    protected void getUserInfo(final LoginResult login_result) {

        GraphRequest data_request = GraphRequest.newMeRequest(
                login_result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Log.e("<><>jsonFB response", "" + json_object.toString());
                        //FB HANDLING
                        requestFacebookUserData(login_result.getAccessToken());
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }
    public void requestFacebookUserData(final AccessToken token) {

        GraphRequest request = GraphRequest.newMeRequest(
                token, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            Log.v("GRAPH RESPONSE", response.toString());
                            Log.v("JSON OBJECT", object.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {

                            String key;

                            key = "id";
                            String id = getValue(key, object);
SharedPrefUtil.setUserId(LoginScreen.this,""+id);
                            key = "gender";
                            String gender = getValue(key, object);


                            key = "email";
                            String email = getValue(key, object);
                            SharedPrefUtil.setUserEmail(LoginScreen.this,""+email);

                            key = "first_name";
                            String first_name = getValue(key, object);
                            SharedPrefUtil.setUserFirstName(LoginScreen.this,""+first_name);

                            key = "last_name";
                            String last_name = getValue(key, object);


                            Log.d("SignInSocialFb ", "key" + key + " \n id " + id + "\n" + "gender " + gender + "\n" + "email " + email + "\n"
                                    + "first_name " + first_name + "\n" + "last_name " + last_name
                                    + "\n" + "token " + token.getToken());


                            LoginManager.getInstance().logOut();

/*
                            if (email == null || TextUtils.isEmpty(email)) {
                                // showErrorDialog(getResources().getString(R.string.We_are_unable_to_get_your_email_id_from_login_Please_use_Google_or_manual_Login_to_access_the_app));
                                CommonUtils.showSnackbar(SignInActivity.this, "We are unable to get your email or mobile from facebook, please use google or manual login to access the app");


                                Intent intent = new Intent(getApplication(), SignUpActivity.class);
                                intent.putExtra("id", "" + id);
                                intent.putExtra("firstName", "" + first_name);
                                intent.putExtra("lastName", "" + last_name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();


                            } else {


                                hitRegisterOneSocialApi("" + id, "" + first_name, "" + last_name, "" + email, "facebook");


                            }
*/

                            Intent intent = new Intent(getApplication(), MainActivity.class);
                     //       intent.putExtra("id", "" + id);
                     //       intent.putExtra("firstName", "" + first_name);
                      //      intent.putExtra("lastName", "" + last_name);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } catch (JSONException jEx) {

                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email, gender, birthday, about, first_name, last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private String getValue(String key, JSONObject jObj) throws JSONException {

        if (jObj.has(key))
            return jObj.getString(key);

        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initComponent() {
        btn_Login = (TextView) findViewById(R.id.btn_Login);
        et_PhoneNum = (EditText) findViewById(R.id.et_PhoneNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_Login:

                String strPhone = et_PhoneNum.getText().toString();
                if ((strPhone.isEmpty() || strPhone.equalsIgnoreCase("")) && strPhone.length()!=10  ) {
                    Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                  /*  */
               callGetOtpApi(strPhone);

                }

                break;

        }
    }

    private void callGetOtpApi(String strPhone) {
        Log.e(TAG+" callGetOtpApi","generateOTP/phone="+   strPhone);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
         Call<AllApiResponse.OtpResponse> call = service.getOtpApi(strPhone);

        call.enqueue(new Callback<AllApiResponse.OtpResponse>() {
            @Override
            public void onResponse(Call<AllApiResponse.OtpResponse> call, Response<AllApiResponse.OtpResponse> response) {
             //   progress_bar.setVisibility(View.GONE);
              //  CharSequence result =new  Gson().toJson(response);
Log.e(TAG+" callGetOtpApi","response   "+   response .toString());
            if(response.body().getStatus().equalsIgnoreCase("200"))
            {
                String strOTP=""+response.body().getData().getOtp();
           //     String strUserID=""+response.body().getUserID();

                Log.e(TAG+" callGetOtpApi","strOTP="+   strOTP);

                Intent intentLogin = new Intent(LoginScreen.this, VerifyOtp.class);

                Bundle mBundle = new Bundle();
                mBundle.putString("otp", strOTP);
               mBundle.putString("phone", et_PhoneNum.getText().toString());

                intentLogin.putExtras(mBundle);
                startActivity(intentLogin);
                finish();
            }

            }
             @Override
            public void onFailure(Call<AllApiResponse.OtpResponse> call, Throwable t) {
               // progress_bar.setVisibility(View.GONE);
                Toast.makeText(LoginScreen.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intentLogin = new Intent(LoginScreen.this, LoginSignUp.class);
        startActivity(intentLogin);
        finish();
    }
}
