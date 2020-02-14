package com.example.lenovo.emptypro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.R;

public class LoginSignUp extends AppCompatActivity implements View.OnClickListener {

    Button btn_gotoSignup,btn_gotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
    initComponent();
        btn_gotoLogin.setOnClickListener(this);
        btn_gotoSignup.setOnClickListener(this);
    }

    private void initComponent() {
        btn_gotoSignup =(Button)findViewById(R.id.btn_gotoSignup);
        btn_gotoLogin=(Button)findViewById(R.id.btn_gotoLogin);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_gotoLogin:
                Intent intLogin= new Intent(LoginSignUp.this,LoginScreen.class);
                startActivity(intLogin);
                finish();
                break;
            case R.id.btn_gotoSignup:
                Intent intSignUp= new Intent(LoginSignUp.this,LoginScreen.class);
                startActivity(intSignUp);
                finish();

                break;

        }
    }
}
