package com.clase.engenios_manuelimdbapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clase.engenios_manuelimdbapp.api.IMDBApiService;
import com.clase.engenios_manuelimdbapp.models.MovieOverviewResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Manuel
 * @version  1.0*/

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView imagenPeli = null; // Variable de la película para cargar la portada
    private TextView titleView = null, valora = null, fecha = null, descrip = null; // Textview para cargar la información
    private IMDBApiService imdbApiService = null; // Instacia del servicio de la API
    private Button enviarSms = null; // Variable del botón para cuando queramos compartir una película por SMS
    private static final int CONTACTS_PERMISSION_REQUEST_CODE = 1; // Permiso de acceso a los contactos
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 2; // Permiso de acceso a enviar SMS
    Toast mensajeToast = null; // Variable para controlar los toast de la aplicación
    private ActivityResultLauncher<Intent> contactPickerLauncher = null; // Variable para
    private String selectedContactNumber = null; // Variable en donde guardo el número al que le voy a enviar la película
    private String movieMessage = "Mira!! Está película te puede gustar "; // Mensaje que completaré después y enviaré al contacto
    MovieOverviewResponse movie = null; // Objeto del tipo MovieOverview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtengo todos los objetos de la interfaz de está actividad
        imagenPeli = (ImageView) findViewById(R.id.imagenPeli);
        titleView = (TextView) findViewById(R.id.txtTituloPeli);
        fecha = (TextView) findViewById(R.id.txtFe);
        valora = (TextView) findViewById(R.id.txtRating);
        descrip = (TextView) findViewById(R.id.txtDescr);
        enviarSms = (Button) findViewById(R.id.btnCompartirSms);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", "45157d396bmshf14702227e85da3p1ff7a9jsna860971b474a")
                            .addHeader("X-RapidAPI-Host", "imdb-com.p.rapidapi.com")
                            .build();
                    return chain.proceed(newRequest);
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://imdb-com.p.rapidapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imdbApiService = retrofit.create(IMDBApiService.class);

        // Obtengo el parceable que le pasé, para ahora cargar la información de la pelicula
        movie = getIntent().getParcelableExtra("movieDetails");
        // Compruebo que recibo algo
        if (movie != null) { // En caso de que si que reciba algo
            // Llamo al método para cargar los detalles de la película
            loadMovieDetails(movie);
        }

        contactPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri contactUri = result.getData().getData();
                        if (contactUri != null) {
                            //showToast("Contacto seleccionado: " + contactUri.toString());
                            extractPhoneNumber(contactUri);
                        }
                    } else {
                        showToast("No se seleccionó ningún contacto");
                    }
                }
        );

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE
            );
        }

        // Establezco un evento para cuando hagamos un click en el botón de compartir por sms
        enviarSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        MovieDetailsActivity.this,
                        Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MovieDetailsActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            CONTACTS_PERMISSION_REQUEST_CODE
                    );

                } else {
                    showToast("Permisos de lectura de contactos ya concedidos");
                    openContactPicker();
                }
            }
        });
    }

    /**
     * Método para */
    private void openContactPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactPickerLauncher.launch(intent);
    }

    /**
     * @param contactUri
     * Método para */
    private void extractPhoneNumber(Uri contactUri) {
        String contactId = null;

        try (Cursor cursor = getContentResolver().query(contactUri,
                new String[]{ContactsContract.Contacts._ID}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                contactId = cursor.getString(idIndex);
            }
        }

        if (contactId != null) {
            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
            String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
            String[] selectionArgs = {contactId};

            try (Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    null)) {

                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    int numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    selectedContactNumber = phoneCursor.getString(numberIndex);

                    if (selectedContactNumber != null && !selectedContactNumber.trim().isEmpty()) {
                        showToast("Número seleccionado: " + selectedContactNumber);

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    SEND_SMS_PERMISSION_REQUEST_CODE
                            );
                        } else {
                            openSmsApp();
                        }
                    } else {
                        showToast("El contacto no tiene un número de teléfono válido.");
                    }
                } else {
                    showToast("No se pudo obtener el número de teléfono.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("Error al obtener el número del contacto.");
            }
        } else {
            showToast("No se pudo obtener el ID del contacto.");
        }
    }

    /**
     * Método para*/
    private void openSmsApp() {
        if (selectedContactNumber != null && !selectedContactNumber.trim().isEmpty()) {
            try {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + Uri.encode(selectedContactNumber)));
                smsIntent.putExtra("sms_body", movieMessage+movie.getTitle()+" con valoración: "+movie.getRanking());
                startActivity(smsIntent);  // Lanzamos la actividad para enviar el SMS
            } catch (Exception e) {
                Log.e("SMS_INTENT_ERROR", "Error al abrir la app de SMS", e);
                showToast("No se pudo abrir la aplicación de SMS.");
            }
        } else {
            showToast("No se pudo obtener un número válido para el SMS.");
        }
    }

    /**
     * @param permissions
     * @param grantResults
     * @param requestCode
     * Método en el que comprobamos si tenemos los permisos activados comprobando
     * los parametros que pasamos para ver si vamos a comprobar el permiso de acceso a contactos
     * o el de poder enviar sms*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACTS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permiso de contactos concedido");
                openContactPicker();
            } else {
                showToast("Permiso de contactos denegado");
            }
        } else if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permiso de envío de SMS concedido");
            } else {
                showToast("Permiso de envío de SMS denegado");
            }
        }
    }

    /**
     * @param movie
     * Método para */
    private void loadMovieDetails(MovieOverviewResponse movie) {
        titleView.setText(movie.getTitle());
        fecha.setText("Release Date: " + movie.getYear());
        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.baseline_autorenew_24)
                .into(imagenPeli);
        Log.d("API_RESPONSE", "ID Movie: " + movie.getId());

        Call<MovieOverviewResponse> call = imdbApiService.getMovieOverview(movie.getId());
        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(Call<MovieOverviewResponse> call, Response<MovieOverviewResponse> response) {
                Log.d("API_RESPONSE", "Código HTTP: " + response.code());
                Log.d("API_RAW_RESPONSE", response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RAW_RESPONSE", response.raw().toString()); // JSON crudo
                    Log.d("API_RAW_BODY", response.body() != null ? response.body().toString() : "Body es null");
                    try {
                        String plotText = response.body().getPlot() != null &&
                                response.body().getPlot().getPlotText() != null &&
                                response.body().getPlot().getPlotText().getPlainText() != null
                                ? response.body().getPlot().getPlotText().getPlainText()
                                : "Descripción no disponible.";

                        descrip.setText(plotText);

                        double rating = response.body().getRatingsSummary() != null
                                ? response.body().getRatingsSummary().getAggregateRating()
                                : 0;

                        valora.setText(rating > 0 ? "Rating: " + rating : "Rating no disponible.");
                    } catch (Exception e) {
                        Log.e("API_PARSE_ERROR", "Error procesando la respuesta: " + e.getMessage());
                        descrip.setText("Error al cargar la descripción.");
                        valora.setText("Error al cargar el rating.");
                    }
                } else {
                    Log.e("API_ERROR", "Respuesta no exitosa. Código: " + response.code());
                    descrip.setText("Descripción no disponible.");
                    valora.setText("Rating no disponible.");
                }
            }

            @Override
            public void onFailure(Call<MovieOverviewResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                descrip.setText("Error al cargar la descripción.");
                valora.setText("Error al cargar el rating.");
            }
        });
    }


    /**
     * @param mensaje
     * Método para ir matando los Toast y mostrar todos en el mismo para evitar
     * colas de Toasts y que se ralentice el dispositivo*/
    public void showToast(String mensaje){
        // Comprobamos si existe algun toast cargado en el toast de la variable global
        if (mensajeToast != null) { // En caso de que si que exista
            mensajeToast.cancel(); // Le cancelamos, es decir le "matamos"
        }

        // Creamos un nuevo Toast con el mensaje que nos dan de argumento en el método
        mensajeToast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        // Mostramos dicho Toast
        mensajeToast.show();
    }
}