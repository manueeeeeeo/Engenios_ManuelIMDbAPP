package com.clase.engenios_manuelimdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Inicio extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth auth=null; // Variable controlar la autenticación de firebase
    private SignInButton signInButton=null; // Botón para iniciar sesión con google
    private GoogleSignInClient googleSignInClient=null;
    private ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Inicializo el Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Configuro el botón de inicio de sesión
        signInButton = findViewById(R.id.sign_in_button);
        // Llamo al método para poder cambiar el texto del botón de inicio de sesión
        cambiarLetrasBoton();
        // Establezco un evento para cuando toco el botón de inicio
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llamo al método para iniciar sesión en Google
                signInWithGoogle();
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .requestProfile()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Configuro el ActivityResultLauncher para manejar los resultados
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch (ApiException e) {
                            Log.w("Inicio", "Google sign-in failed", e);
                        }
                    }
                }
        );
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    // Autenticación con Firebase
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(Inicio.this, MainActivity.class);
                            intent.putExtra("name", user.getDisplayName());
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("photoUrl", user.getPhotoUrl().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w("Inicio", "signInWithCredential:failure", task.getException());
                    }
                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(Inicio.this, MainActivity.class);
            intent.putExtra("name", currentUser.getDisplayName());
            intent.putExtra("email", currentUser.getEmail());
            intent.putExtra("photoUrl", currentUser.getPhotoUrl().toString());
            startActivity(intent);
            finish();
        }
    }

    /**
     * Método para */
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