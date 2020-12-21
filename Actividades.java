package com.example.tfgv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Actividades extends AppCompatActivity {
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    //Utiles
    private Button btnCrear;
    //Captura de datos
    private EditText nameActivity;
    private EditText descrpActivity;
    private EditText fchInicioActivity;
    private EditText fchFinActivity;
    private RadioButton rulesActivity;
    private ArrayList<Actividad> listActividades = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //
        nameActivity = (EditText) findViewById(R.id.nameInput);
        descrpActivity = (EditText) findViewById(R.id.descrInput);
        fchInicioActivity = (EditText) findViewById(R.id.fchInicioInput);
        fchFinActivity = (EditText) findViewById(R.id.fchFinInput);
        rulesActivity = (RadioButton) findViewById(R.id.rdPhoneData);
        //
        btnCrear = (Button) findViewById(R.id.btnCreate);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameActivity.getText().toString() == "" && descrpActivity.getText().toString() == "" && fchInicioActivity.getText().toString() == "" && fchFinActivity
                        .getText().toString() == "") {
                    Toast.makeText(Actividades.this, "Debes rellenar todos los campos obligatorios",
                            Toast.LENGTH_SHORT).show();
                } else {
                    createActivity();
                    startActivity(new Intent(Actividades.this, Profile.class));
                    finish();
                }
            }
        });

    }

    private void createActivity() {
        String permisosData = "";
        if (!rulesActivity.isSelected()) {
            permisosData = "Si";
        } else {
            permisosData = "No";
        }
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("name", nameActivity.getText().toString());
        mapa.put("description", descrpActivity.getText().toString());
        mapa.put("startTime", fchInicioActivity.getText().toString());
        mapa.put("finishTime", fchFinActivity.getText().toString());
        mapa.put("notificaciones", permisosData);
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).child("Lista Actividades").push().setValue(mapa);
    }
}