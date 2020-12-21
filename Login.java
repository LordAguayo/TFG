package com.example.tfgv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    //Variables firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    //Variables inputs
    private Button btnStart;
    private EditText userInput;
    private EditText emailInput;
    private EditText passInput;
    private Button btnRegistrar;
    private Button btnGoToLogin;
    //Variables datos capturados
    private String userData = "";
    private String emailData = "";
    private String passData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userInput = (EditText) findViewById(R.id.userText);
        emailInput = (EditText) findViewById(R.id.emailText);
        passInput = (EditText) findViewById(R.id.passLoginText);
        btnGoToLogin = (Button) findViewById(R.id.btnComenzar);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Login.class));
                finish();
            }
        });
        btnRegistrar = (Button) findViewById(R.id.btnComenzar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData = userInput.getText().toString();
                emailData = emailInput.getText().toString();
                passData = passInput.getText().toString();

                if (!emailData.isEmpty() && !passData.isEmpty()) {
                    if (passData.length() >= 6) {
                        registerUser();
                    } else {
                        Toast.makeText(Login.this, "Su contraseña debe ser del al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Login.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo privado que permite efectuar una conexion con la base de datos Firebase y efectuar la creación de un usuario.
    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(emailData, passData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("user", userData);
                    map.put("email", emailData);
                    map.put("pass", passData);
                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(Login.this, Profile.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, "We can't create your user.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Register failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Profile.class));
            finish();
        }
    }
}