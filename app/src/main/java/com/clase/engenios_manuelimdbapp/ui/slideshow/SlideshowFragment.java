package com.clase.engenios_manuelimdbapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.txtUser);
        TextView userEmailTextView = headerView.findViewById(R.id.txtCorreo);

        userName = userNameTextView.getText().toString();
        userEmail = userEmailTextView.getText().toString();

        TextView textView = binding.textSlideshow;
        textView.setText("Welcome, User"  + " (" + userEmail + ")");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}