package com.example.appdonacion.ui.slideshow;

import android.app.ProgressDialog;
import android.os.Bundle;
//import android.view.View;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appdonacion.DonacionSharePreferences;
import com.example.appdonacion.R;
import com.google.firebase.storage.FirebaseStorage;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Configuraciones");
    }

    public LiveData<String> getText() {
        return mText;
    }



}