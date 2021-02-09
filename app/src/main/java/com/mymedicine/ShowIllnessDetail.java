package com.mymedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

public class ShowIllnessDetail extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_illness_detail);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);


        String json=getIntent().getStringExtra("illness");
        Gson gson=new Gson();
        Illness illness=gson.fromJson(json,Illness.class);


        tv1.setText(illness.illness);
        tv2.setText(illness.medicine);
        tv3.setText(illness.symptom);
        tv4.setText(illness.description);
        tv5.setText(illness.cause);

        Log.e("illness",illness.toString());
    }
}
