package com.example.dietaryNutrition.ui.notice_page;

import android.content.Intent;
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
import com.example.dietaryNutrition.login_register.login.Login;

public class NoticePageFragment extends Fragment {

    private NoticePageViewModel noticePageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noticePageViewModel =
                ViewModelProviders.of(this).get(NoticePageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notice_page, container, false);
        final TextView textView = root.findViewById(R.id.text_notice_page);
        noticePageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        startActivity(new Intent(getActivity(), Login.class));
    }
}