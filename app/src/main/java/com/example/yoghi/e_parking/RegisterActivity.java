package com.example.yoghi.e_parking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button btn_daftar;
    EditText RegEmail, RegPass;
    TextView RegMasuk;

    //private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        btn_daftar = findViewById(R.id.btn_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = RegEmail.getText().toString().trim();
                String Pass = RegPass.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(RegisterActivity.this, "Masukkan Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Pass)) {
                    Toast.makeText(RegisterActivity.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                    return;
                }
//                ProgressDialog.setMessage("Registering User...");
//                ProgressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(Email, Pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                    Toast.makeText(RegisterActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        RegEmail = findViewById(R.id.RegEmail);

        RegPass = findViewById(R.id.RegPass);

        RegMasuk = findViewById(R.id.RegMasuk);
        RegMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
