package com.example.tfgv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private Button btnGoBack;
    private ListView lista;
    private ArrayList<Actividad> lstActividades;
    private Actividad x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        lstActividades = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lista = (ListView) findViewById(R.id.listaActividades);

        //Regresar al perfil
        btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Listar.this, Profile.class));
                finish();
            }
        });
        getData();
        seleccionClick();
    }

    private void getData() {

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).child("Lista Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mDatabase.child("Users").child(id).child("Lista Actividades")
                            .child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Actividad datos = snapshot.getValue(Actividad.class);
                            String key = dataSnapshot.getKey();
                            String nombre = datos.getName();
                            String descripcion = datos.getDescription();
                            String fchInicio = datos.getStartTime();
                            String fchFin = datos.getFinishTime();
                            String rules = key;

                            Actividad x = new Actividad(key, nombre, descripcion, fchInicio, fchFin, rules);
                            lstActividades.add(x);
                            ArrayAdapter<Actividad> adapter = new ArrayAdapter<Actividad>(Listar.this,
                                    android.R.layout.simple_list_item_1, lstActividades);
                            lista.setAdapter(adapter);

                            Log.e("Key", "" + key);
                            Log.e("Nombre", "" + nombre);
                            Log.e("Descripcion", "" + descripcion);
                            Log.e("Fecha Inicio", "" + fchInicio);
                            Log.e("Fecha Fin", "" + fchFin);
                            Log.e("Notif", "" + rules);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seleccionClick() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Actividad x = lstActividades.get(i);
                Intent intent = new Intent(Listar.this, Editar.class);
                intent.putExtra("name", x.getName().toString());
                intent.putExtra("descripcion", x.getDescription().toString());
                intent.putExtra("start", x.getStartTime().toString());
                intent.putExtra("finish", x.getFinishTime().toString());
                intent.putExtra("rules", x.getPermisos());
                startActivity(intent);
            }
        });
    }
}

