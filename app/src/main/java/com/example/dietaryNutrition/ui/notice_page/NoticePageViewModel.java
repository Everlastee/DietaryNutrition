package com.example.dietaryNutrition.ui.notice_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoticePageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NoticePageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notice page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}