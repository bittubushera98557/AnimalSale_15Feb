package com.example.lenovo.emptypro.History;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lenovo.emptypro.R;

public class HistoryScreen extends AppCompatActivity implements View.OnClickListener {
    EditText et_mySale, et_myBids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);
    initComponets();
        et_mySale.setOnClickListener(this);
        et_mySale.setFocusable(false);
        //et_mySale.setEnabled(false);
        et_myBids.setOnClickListener(this);
        et_myBids.setFocusable(false);
        //et_myBids.setEnabled(false);
        }

    private void initComponets() {
        et_myBids=(EditText)findViewById(R.id.et_myBids);
        et_mySale=(EditText)findViewById(R.id.et_mySale);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.et_mySale:
                et_mySale.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_myBids.setTextColor(getResources().getColor(R.color.colorBlack));
et_mySale.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP);
                et_myBids.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack),
                        PorterDuff.Mode.SRC_ATOP);

                //callSaleHistory()
                break;
            case R.id.et_myBids:
                et_myBids.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_mySale.setTextColor(getResources().getColor(R.color.colorBlack));
                et_myBids.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary),
                        PorterDuff.Mode.SRC_ATOP);
                et_mySale.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack),
                        PorterDuff.Mode.SRC_ATOP);

//callSaleHistory()
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
