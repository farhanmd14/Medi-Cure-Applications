package com.mymedicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main_illness extends AppCompatActivity {
    int position;
    String selected;

    Spinner ill;

    ArrayList<Illness> child = new ArrayList<>();
    ArrayList<Illness> men = new ArrayList<>();
    ArrayList<Illness> women = new ArrayList<>();

    List<String> child_title = new ArrayList<>();
    List<String> men_title = new ArrayList<>();
    List<String> women_title = new ArrayList<>();
    RadioButton rb_child, rb_men, rb_women;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_illness);
        ill = findViewById(R.id.ill);
        rb_child = findViewById(R.id.rb_child);
        rb_men = findViewById(R.id.rb_man);
        rb_women = findViewById(R.id.rb_women);

        populatedata();

        alertDialog();

        ill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Main_illness.this.position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void alertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final SharedPreferences sp = getSharedPreferences("setting", 0);
        alertDialog.setTitle("Important");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.ic_add_alert_black_24dp);
        alertDialog.setMessage("This app is only for \n Education purpose only.");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                sp.edit().putBoolean("acknowleged", true).apply();
            }
        });
        if (!sp.getBoolean("acknowleged", false))
            alertDialog.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_log, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_first) {
            Intent intent = new Intent(Main_illness.this, Login.class);
            startActivity(intent);
        }
        return true;
    }

    private void populatedata() {
        Illness illness;

        illness = new Illness(getResources().getString(R.string.cold_illness), getResources().getString(R.string.cold_description), getResources().getString(R.string.cold_cause), getResources().getString(R.string.cold_medicine), getResources().getString(R.string.cold_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.cough_illness), getResources().getString(R.string.cough_description), getResources().getString(R.string.cough_cause), getResources().getString(R.string.cough_medicine), getResources().getString(R.string.cough_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.body_illness), getResources().getString(R.string.body_description), getResources().getString(R.string.body_cause), getResources().getString(R.string.body_medicine), getResources().getString(R.string.body_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.pneumonia_illness), getResources().getString(R.string.pneumonia_description), getResources().getString(R.string.pneumonia_cause), getResources().getString(R.string.pneumonia_medicine), getResources().getString(R.string.pneumonia_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.sore_illness), getResources().getString(R.string.sore_description), getResources().getString(R.string.sore_cause), getResources().getString(R.string.sore_medicine), getResources().getString(R.string.sore_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.child_illness), getResources().getString(R.string.child_description), getResources().getString(R.string.child_cause), getResources().getString(R.string.child_medicine), getResources().getString(R.string.child_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.childa_illness), getResources().getString(R.string.childa_description), getResources().getString(R.string.childa_cause), getResources().getString(R.string.childa_medicine), getResources().getString(R.string.childa_symptoms));
        child.add(illness);
        illness = new Illness(getResources().getString(R.string.childb_illness), getResources().getString(R.string.childb_description), getResources().getString(R.string.childb_cause), getResources().getString(R.string.childb_medicine), getResources().getString(R.string.childb_symptoms));
        child.add(illness);


        illness = new Illness(getResources().getString(R.string.men_illness), getResources().getString(R.string.men_description), getResources().getString(R.string.men_cause), getResources().getString(R.string.men_medicine), getResources().getString(R.string.men_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.mena_illness), getResources().getString(R.string.mena_description), getResources().getString(R.string.mena_cause), getResources().getString(R.string.mena_medicine), getResources().getString(R.string.mena_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.menb_illness), getResources().getString(R.string.menb_description), getResources().getString(R.string.menb_cause), getResources().getString(R.string.menb_medicine), getResources().getString(R.string.menb_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.menc_illness), getResources().getString(R.string.menc_description), getResources().getString(R.string.menc_cause), getResources().getString(R.string.menc_medicine), getResources().getString(R.string.menc_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.mend_illness), getResources().getString(R.string.mend_description), getResources().getString(R.string.mend_cause), getResources().getString(R.string.mend_medicine), getResources().getString(R.string.mend_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.mene_illness), getResources().getString(R.string.mene_description), getResources().getString(R.string.mene_cause), getResources().getString(R.string.mene_medicine), getResources().getString(R.string.mene_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.menf_illness), getResources().getString(R.string.menf_description), getResources().getString(R.string.menf_cause), getResources().getString(R.string.menf_medicine), getResources().getString(R.string.menf_symptoms));
        men.add(illness);
        illness = new Illness(getResources().getString(R.string.meng_illness), getResources().getString(R.string.meng_description), getResources().getString(R.string.meng_cause), getResources().getString(R.string.meng_medicine), getResources().getString(R.string.meng_symptoms));
        men.add(illness);


        illness = new Illness(getResources().getString(R.string.women_illness), getResources().getString(R.string.women_description), getResources().getString(R.string.women_cause), getResources().getString(R.string.women_medicine), getResources().getString(R.string.women_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womena_illness), getResources().getString(R.string.womena_description), getResources().getString(R.string.womena_cause), getResources().getString(R.string.womena_medicine), getResources().getString(R.string.womena_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womenb_illness), getResources().getString(R.string.womenb_description), getResources().getString(R.string.womenb_cause), getResources().getString(R.string.womenb_medicine), getResources().getString(R.string.womenb_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womenc_illness), getResources().getString(R.string.womenc_description), getResources().getString(R.string.womenc_cause), getResources().getString(R.string.womenc_medicine), getResources().getString(R.string.womenc_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womend_illness), getResources().getString(R.string.womend_description), getResources().getString(R.string.womend_cause), getResources().getString(R.string.womend_medicine), getResources().getString(R.string.womend_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womene_illness), getResources().getString(R.string.womene_description), getResources().getString(R.string.womene_cause), getResources().getString(R.string.womene_medicine), getResources().getString(R.string.womene_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womenf_illness), getResources().getString(R.string.womenf_description), getResources().getString(R.string.womenf_cause), getResources().getString(R.string.womenf_medicine), getResources().getString(R.string.womenf_symptoms));
        women.add(illness);
        illness = new Illness(getResources().getString(R.string.womeng_illness), getResources().getString(R.string.womeng_description), getResources().getString(R.string.womeng_cause), getResources().getString(R.string.womeng_medicine), getResources().getString(R.string.womeng_symptoms));
        women.add(illness);




        for (Illness ill : child) {
            child_title.add(ill.illness);
        }

        for (Illness ill : men) {
            men_title.add(ill.illness);
        }

        for (Illness ill : women) {
            women_title.add(ill.illness);
        }

        baby(null);
    }

    public void baby(View view) {

        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, child_title);
        ill.setAdapter(adapter);
        selected = "child";
        rb_child.setChecked(true);
    }

    public void men(View view) {
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, men_title);
        ill.setAdapter(adapter);
        selected = "men";
        rb_men.setChecked(true);


    }

    public void women(View view) {
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, women_title);
        ill.setAdapter(adapter);
        selected = "women";
        rb_women.setChecked(true);

    }

    public void sub(View view) {
        Log.e("selected", selected + position);
        Gson gson = new Gson();
        Illness illness;

        if (selected.equals("child")) {
            illness = child.get(position);
        } else if (selected.equals("men")) {
            illness = men.get(position);
        } else if (selected.equals("women")) {
            illness = women.get(position);
        } else {
            Toast.makeText(this, "Select Illness", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(this, ShowIllnessDetail.class).putExtra("illness", gson.toJson(illness)));

    }
}
