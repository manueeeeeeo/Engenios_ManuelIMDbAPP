package com.clase.engenios_manuelimdbapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.clase.engenios_manuelimdbapp.R;
import com.clase.engenios_manuelimdbapp.databinding.FragmentSlideshowBinding;
import com.google.android.material.navigation.NavigationView;

public class SlideshowFragment extends Fragment {

    private String userName="";
    private String userEmail="";
    private String userPhotoUrl="";
    private FragmentSlideshowBinding binding=null;
    private EditText busqueda = null;
    private Button botonBuscar = null;
    private Spinner spinnerGenero = null;
    private Toast mensajeToast = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        busqueda = (EditText) binding.editFechaPeli;
        botonBuscar = (Button) binding.btnBuscarPeli;
        spinnerGenero = (Spinner) binding.spinnerCategorias;

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return root;
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