package com.example.dietaryNutrition.ui.main_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.dietaryNutrition.R;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mainPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainPageViewModel =
                ViewModelProviders.of(this).get(MainPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_page, container, false);
        final TextView textView = root.findViewById(R.id.text_main_page);
        mainPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}