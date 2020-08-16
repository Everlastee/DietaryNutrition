package com.example.dietaryNutrition.ui.plan_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlanPageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlanPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is plan page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}