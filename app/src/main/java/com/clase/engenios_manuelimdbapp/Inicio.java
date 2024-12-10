package com.clase.engenios_manuelimdbapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity {
    private FirebaseAuth auth;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        auth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cambiarLetrasBoton();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        //updateUI(currentUser);
    }

    // Cambia el texto del botón
    public void cambiarLetrasBoton(){
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            android.view.View view = signInButton.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setText("Sign in with Google");
                break;
            }
        }
    }
}