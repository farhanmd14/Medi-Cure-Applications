package com.mymedicine;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Sign_up extends AppCompatActivity {
    EditText name, email, pas, con_pas, dob;
    Button submit;
    RadioGroup gender;

    RadioButton male, female, other;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pas = findViewById(R.id.pas);
        con_pas = findViewById(R.id.con_pas);
        submit = findViewById(R.id.submit);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);
        dob = findViewById(R.id.dob);

    }

    public void log(View view) {
        final String umail = email.getText().toString();
        String upassword = pas.getText().toString();
        String confirm = con_pas.getText().toString();

        if (umail.isEmpty()) {
            Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show();
            email.setError("enter email");
            return;
        }
        if (upassword.isEmpty()) {
            Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show();
            pas.setError("enter email");
            return;
        }
        if (!upassword.equals(confirm)) {
            Toast.makeText(this, "enter same password", Toast.LENGTH_SHORT).show();
            con_pas.setError("password don't match");
            return;
        }
        if (dob.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Date Of Birth", Toast.LENGTH_SHORT).show();
            dob.setError("Enter Date Of Birth");
            return;
        }
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String uname = name.getText().toString();
        final String gender;
        if (male.isChecked())
            gender = "Male";
        else if (female.isChecked())
            gender = "Female";
        else if (other.isChecked())
            gender = "Other";
        else {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Registering");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(umail, upassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            /**/
                            Log.e("auth", "successs");
                            User user1 = new User(uname, umail, gender, user.getUid(), dob.getText().toString());
                            uploadUser(user1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Sign_up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                        // ...
                    }
                });


    }

    private void uploadUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Sign_up.this, "Success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(Sign_up.this, Login.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("failed", e.toString());
                dialog.dismiss();
            }
        });

    }

    public void dobb(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void loog(View view) {
        startActivity(new Intent(Sign_up.this,Login.class));
        finish();
    }
}
