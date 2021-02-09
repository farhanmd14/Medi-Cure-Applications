package com.mymedicine;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Contact_us extends Fragment {

    TextView tv3, tv5, tv7;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_contact_us, container, false);
        tv3=root.findViewById(R.id.tv3);
        tv5=root.findViewById(R.id.tv5);
        tv7=root.findViewById(R.id.tv7);

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent se=new Intent(Intent.ACTION_SEND);
                se.setType("message/farhanmd14@gmail.com");
                se.putExtra(Intent.EXTRA_SUBJECT,"THIS IS DEMO SUBJECT");
                se.putExtra(Intent.EXTRA_TEXT,"HELLO, SIR THIS THE THE DEMO MESSAGE.");

                startActivity(se);
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String posted_by = "+91 9711579788";


                String uri = "tel:" + posted_by.trim() ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
                
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

            }
        });


        return root;
    }

}
