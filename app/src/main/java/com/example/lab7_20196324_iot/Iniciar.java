package com.example.lab7_20196324_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab7_20196324_iot.databinding.ActivityIniciarBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Iniciar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivityIniciarBinding binding;
    private FirebaseFirestore db;
    TextView forgotPassword;
    //forgot pass
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forgotPassword = findViewById(R.id.forgot_password);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            redirectUserBasedOnRole(user.getEmail());
        } else {
            setupLoginButton();
        }



    }

    private void setupLoginButton() {
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.correo.getText().toString();
            String password = binding.contrasena.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                performLogin(email, password);
            } else {
                Toast.makeText(Iniciar.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        redirectUserBasedOnRole(email);
                    } else {
                        Toast.makeText(Iniciar.this, "Error en el inicio de sesión.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectUserBasedOnRole(String email) {
        Query query = db.collection("usuarios").whereEqualTo("correo", email);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    // El documento existe, puedes obtener los datos
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    Map<String, Object> data = document.getData();
                    String rol = (String) data.get("rol");
                    String estadoStr = (String) data.get("estado");

                    SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tipoUsuario", rol);
                    editor.apply();

                    if("activo".equals(estadoStr)){
                        switch (rol) {
                            case "gestor":
                                startActivity(new Intent(Iniciar.this, MainGestor.class));
                                break;
                            default:
                                // Para otros roles o si no se especifica
                                startActivity(new Intent(Iniciar.this, MainCliente.class));
                                break;
                        }
                        Toast.makeText(Iniciar.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Iniciar.this, "La cuenta no se encuentra habilitada, comuníquese con el administrador.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El documento no existe
                    Toast.makeText(Iniciar.this, "Usuario no encontrado en la base de datos.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Error al obtener el documento
                Toast.makeText(Iniciar.this, "Error al obtener datos del usuario.", Toast.LENGTH_SHORT).show();
            }
        });
    }



}