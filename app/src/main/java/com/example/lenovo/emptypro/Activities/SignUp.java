package com.example.lenovo.emptypro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.R;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button btn_SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    intiComponent();
        btn_SignUp.setOnClickListener(this);
    }

    private void intiComponent() {
        btn_SignUp=findViewById(R.id.btn_SignUp);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_SignUp:
                Intent intLogin= new Intent(SignUp.this,VerifyOtp.class);
                startActivity(intLogin);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intLogin= new Intent(SignUp.this,LoginSignUp.class);
        startActivity(intLogin);
        finish();
    }
}

