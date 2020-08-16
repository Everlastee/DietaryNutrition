package com.example.dietaryNutrition.ui.recipe_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipePageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecipePageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recipe page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}