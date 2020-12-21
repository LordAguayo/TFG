package com.example.tfgv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private Button btnLogout;
    private TextView userID;
    private Button listaUser;
    private Button newActivity;
    private ListView lView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userID = (TextView) findViewById(R.id.userNameTitle);
        btnLogout = (Button) findViewById(R.id.btnLogOut);
        listaUser = (Button) findViewById(R.id.btnShowList);
        newActivity = (Button) findViewById(R.id.btnNewActivity);
        lView = (ListView) findViewById(R.id.listaActividades);
        //
        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Actividades.class));
            }
        });
        listaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Listar.class));
            }

        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
                finish();
            }
        });
        getInfo();
    }

    //Metodo para identificar al usuario conectado.
    private void getInfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("user").getValue().toString();
                    userID.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
