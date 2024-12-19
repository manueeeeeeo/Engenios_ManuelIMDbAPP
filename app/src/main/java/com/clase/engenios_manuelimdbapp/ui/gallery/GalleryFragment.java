package com.clase.engenios_manuelimdbapp.ui.gallery;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clase.engenios_manuelimdbapp.adapters.FavoriteAdapters;
import com.clase.engenios_manuelimdbapp.databinding.FragmentGalleryBinding;
import com.clase.engenios_manuelimdbapp.models.FavoriteMoviesDatabase;
import com.clase.engenios_manuelimdbapp.models.Movie;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private RecyclerView recy = null;
    private Button botonCompartir = null;
    private static final int SHARE_BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
    Toast mensajeToast = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recy = (RecyclerView) binding.recycle2;
        recy.setLayoutManager(new LinearLayoutManager(getContext()));

        FavoriteMoviesDatabase database = new FavoriteMoviesDatabase(getContext());

        List<Movie> favoriteMoviesList = database.getAllFavorites();

        FavoriteAdapters adapter = new FavoriteAdapters(getContext(), favoriteMoviesList);
        recy.setAdapter(adapter);

        botonCompartir = (Button) binding.btnCompartirFavs;
        botonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }

    /**
     * Método para manejar el resultado de la solicitud de permisos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SHARE_BLUETOOTH_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permiso de Bluetooth concedido");
            } else {
                showToast("Permiso de Bluetooth denegado");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
        mensajeToast = Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT);
        // Mostramos dicho Toast
        mensajeToast.show();
    }
}