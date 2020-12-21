package com.example.tfgv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Editar extends AppCompatActivity {


    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private String ruta;
    private Button btnToList;
    private Button btnBorrar;
    private Button btnActualizar;
    private TextView nameTitle2;
    private EditText nameActivity2;
    private EditText descrpActivity2;
    private EditText fchInicioActivity2;
    private EditText fchFinActivity2;
    private RadioButton rulesActivity2;
    private String nombre;
    private String descripcion;
    private String fchI;
    private String fchF;
    private String rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        ruta = "";
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnActualizar = (Button) findViewById(R.id.btnUpdate);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        btnBorrar = (Button) findViewById(R.id.btnDelete);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        btnToList = (Button) findViewById(R.id.btnToList);
        btnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Editar.this, Listar.class));
            }
        });

        ////////

        nameTitle2 = (TextView) findViewById(R.id.textTitleActividad);
        nameActivity2 = (EditText) findViewById(R.id.nameInput2);
        descrpActivity2 = (EditText) findViewById(R.id.descrInput2);
        fchInicioActivity2 = (EditText) findViewById(R.id.fchInicioInput2);
        fchFinActivity2 = (EditText) findViewById(R.id.fchFinInput2);
        rulesActivity2 = (RadioButton) findViewById(R.id.rdPhoneData2);
        /////////

        Bundle extras = getIntent().getExtras();
        ruta = extras.getString("rules");
        nombre = extras.getString("name");
        descripcion = extras.getString("descripcion");
        fchI = extras.getString("start");
        fchF = extras.getString("finish");
        System.out.println(ruta + "" + nombre + "" + descripcion + "" + fchI + "" + fchF + "" + rules);
        getExtras();

    }

    private void delete() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).child("Lista Actividades").child(ruta).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(Editar.this, Listar.class));
                finish();
                Toast.makeText(Editar.this, "\"Se ha eliminado la actividad\"",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void actualizar() {
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> dataUpdate = new HashMap<>();
        dataUpdate.put("name", nameActivity2.getText().toString());
        dataUpdate.put("description", descrpActivity2.getText().toString());
        dataUpdate.put("startTime", fchInicioActivity2.getText().toString());
        dataUpdate.put("finishTime", fchFinActivity2.getText().toString());
        dataUpdate.put("notificaciones", "SI");

        mDatabase.child("Users").child(id).child("Lista Actividades").child(ruta).updateChildren(dataUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(Editar.this, Listar.class));
                finish();
                Toast.makeText(Editar.this, "\"Se ha actualizado la actividad\"",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExtras() {
        nameTitle2.setText(nombre);
        nameActivity2.setText(nombre);
        descrpActivity2.setText(descripcion);
        fchInicioActivity2.setText(fchI);
        fchFinActivity2.setText(fchF);
        rulesActivity2.setText(rules);

    }
}