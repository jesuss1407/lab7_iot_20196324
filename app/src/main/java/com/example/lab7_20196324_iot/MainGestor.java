package com.example.lab7_20196324_iot;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import com.example.lab7_20196324_iot.adapter.CitasAdapter;
import com.example.lab7_20196324_iot.adapter.DatosSalon;
import com.example.lab7_20196324_iot.entity.CitasDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

public class MainGestor extends AppCompatActivity {
    private FirebaseFirestore db;
    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private List<CitasDto> citasList;
    private CitasAdapter adapter;
    final AccessControlContext context = getContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gestor);


        db = FirebaseFirestore.getInstance();

        //logout
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Iniciar.class));
        });

        //datos
        Button datos = findViewById(R.id.datos);
        datos.setOnClickListener(v -> {
            startActivity(new Intent(this, DatosSalon.class));
        });

        db.collection("citas")
                .whereEqualTo("estado", "activo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CitasDto> citasList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CitasDto cita = document.toObject(CitasDto.class);
                                citasList.add(cita);
                            }

                            // Pasar la lista filtrada al adaptador
                            recyclerView = findViewById(R.id.listarCitas);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainGestor.this));
                            adapter = new CitasAdapter(citasList);
                            recyclerView.setAdapter(adapter);

                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}