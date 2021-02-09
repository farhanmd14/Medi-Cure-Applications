package com.mymedicine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email, pas;
    Button btn_log, btn_reg;
    TextView tv_forget;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(this,Dashboard.class));
            finish();
        }

        email = findViewById(R.id.email);
        pas = findViewById(R.id.pas);
        btn_log = findViewById(R.id.btn_log);
        btn_reg = findViewById(R.id.btn_reg);
        tv_forget = findViewById(R.id.tv_forget);

    }


    public void sign(View view) {
        startActivity(new Intent(Login.this, Sign_up.class));
       // finish();
    }


    public void log(View view) {
        final String umail = email.getText().toString();
        String upassword = pas.getText().toString();
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

        dialog =new ProgressDialog(this);
        dialog.setMessage("Logging You In ");
        dialog.setCancelable(false);
        dialog.show();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(umail, upassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(Login.this, Dashboard.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        // ...
                    }
                });

    }
}

