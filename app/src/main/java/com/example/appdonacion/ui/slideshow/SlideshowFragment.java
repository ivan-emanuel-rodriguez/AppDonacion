package com.example.appdonacion.ui.slideshow;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appdonacion.PaginaPrincipalActivity;
import com.example.appdonacion.R;

import java.util.Locale;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private Button ingles;
    private Button espaniol;
    private Button frances;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ingles = (Button) root.findViewById(R.id.btn_ingles);
        espaniol = (Button) root.findViewById(R.id.btn_esp);
        frances = (Button) root.findViewById(R.id.btn_fr);
        ingles.setOnClickListener(CambiarAEs);
        espaniol.setOnClickListener(ChangeToEn);
        frances.setOnClickListener(ChangeToFr);


        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    private View.OnClickListener CambiarAEs = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CambiarIdioma("en");
        }
    };
    private View.OnClickListener ChangeToEn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CambiarIdioma("es");
        }
    };
    private View.OnClickListener ChangeToFr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CambiarIdioma("fr");
        }
    };
    public void CambiarIdioma(String idioma){
        Locale locale;
        Configuration config = new Configuration();
        locale = new Locale(idioma);
        config.locale =locale;
        getResources().updateConfiguration(config, null);
        getActivity().recreate();
    }
}